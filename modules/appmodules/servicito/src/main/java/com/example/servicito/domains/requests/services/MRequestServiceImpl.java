package com.example.servicito.domains.requests.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.coreweb.utils.PageAttr;
import com.example.common.utils.DateUtil;
import com.example.common.utils.NetworkUtil;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.apartments.services.ApartmentService;
import com.example.servicito.domains.histories.models.entities.RentHistory;
import com.example.servicito.domains.histories.services.RentHistoryService;
import com.example.servicito.domains.notifications.models.pojo.NotificationData;
import com.example.servicito.domains.notifications.models.pojo.PushNotification;
import com.example.servicito.domains.notifications.services.NotificationService;
import com.example.servicito.domains.requests.models.entities.Appointment;
import com.example.servicito.domains.requests.models.entities.MRequest;
import com.example.servicito.domains.requests.repositories.MRequestRepository;
import com.example.acl.domains.users.services.UserService;
import com.example.common.exceptions.exists.MRequestAlreadyExistsException;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.MRequestNotFoundException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import com.example.common.exceptions.nullpointer.NullException;
import com.example.common.exceptions.unknown.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class MRequestServiceImpl implements MRequestService {
    private final MRequestRepository requestRepo;
    private final RentHistoryService historyService;
    private final AppointmentService appointmentService;
    private final ApartmentService apartmentService;
    private final NotificationService notificationService;

    @Value("${app.config.landlordownagent}")
    private boolean isLandlordOwnAgent;
    @Value("${app.config.requestLimit}")
    private int requestLimit;

    @Autowired
    public MRequestServiceImpl(MRequestRepository requestRepo, UserService userService, RentHistoryService historyService, AppointmentService appointmentService, ApartmentService apartmentService, NotificationService notificationService) {
        this.requestRepo = requestRepo;
        this.historyService = historyService;
        this.appointmentService = appointmentService;
        this.apartmentService = apartmentService;
        this.notificationService = notificationService;
    }

    @Override
    public Page<MRequest> getAll(int page) {
        return this.requestRepo.findAll(PageAttr.getPageRequest(page));
    }

    @Override
    public Page<MRequest> findByStatus(String status, int page) {
        return this.requestRepo.findByStatus(status, PageAttr.getPageRequest(page));
    }

    @Override
    public Page<MRequest> getAllByUser(Long userId, int page) {
        return this.requestRepo.findByRequestedById(userId, PageAttr.getPageRequest(page));
    }

    @Override
    public Page<MRequest> getAllByRequestedTo(Long userId, int page) {
        return this.requestRepo.findByRequestedToId(userId, PageAttr.getPageRequest(page));
    }

    @Override
    public Page<MRequest> getAllByAssignedEmployee(Long userId, int page) {
        if (this.isLandlordOwnAgent)
            return this.requestRepo.findByApartmentBuildingLandlordId(userId, PageAttr.getPageRequest(page));
        return this.requestRepo.findByAssignedEmployeeId(userId, PageAttr.getPageRequest(page));
    }

    @Override
    public MRequest getOne(Long id) throws MRequestNotFoundException, ForbiddenException {
        if (id == null || !this.exists(id)) throw new MRequestNotFoundException("Couldn't find request");
        MRequest mRequest = this.requestRepo.findById(id).orElse(null);
        if (mRequest == null) throw new MRequestNotFoundException("Could not find request with id: " + id);
        return mRequest;
    }

    @Override
    public MRequest save(MRequest request) throws InvalidException, MRequestAlreadyExistsException, ForbiddenException {
        this.validateRequest(request);
        request = this.requestRepo.save(request);
        try {
            if (this.isLandlordOwnAgent)
                NetworkUtil.sendSms(
                        request.getRequestedTo().getPhone(),
                        "New request from Varatiya.To view, open servicito app or download from bit.ly/servicito"
                );
            this.notifyAdmin(request);
        } catch (Exception | UnknownException ignored) {
        }
        return request;
    }

    private void notifyAdmin(MRequest request) throws UnknownException, InvalidException, JsonProcessingException {
        NotificationData data = new NotificationData();
        data.setTitle("New request from -:- " + request.getRequestedBy().getUsername());
        String description = "Landlord: " + request.getLandlordInfo().getPhone() + ",\nArea: " + request.getApartment().getBuilding().getAddress().getArea() + ",\nOn: " + DateUtil.getReadableDateTime(new Date());
        String brief = description.substring(0, Math.min(description.length(), 100));
        data.setMessage(brief);
        data.setType(PushNotification.Type.ALERT.getValue());

        PushNotification notification = new PushNotification(null, data);
        notification.setTo("/topics/adminnotifications");
        this.notificationService.sendNotification(notification);
    }

    /*
        1.  Change request status according to status param
        2.  Set assigned employee if request status is received
        3.  Request receive, mark as confirmed
     */
    @Override
    public MRequest changeStatus(Long id, String status, Long assignedEmployeeId, Date confirmedFrom) throws MRequestAlreadyExistsException, InvalidException, ForbiddenException, UserNotFoundException, MRequestNotFoundException {
        MRequest mRequest = this.getOne(id);
        MRequest.Status requestStatus = MRequest.Status.fromValue(status);
        mRequest.setStatus(requestStatus);
        return this.update(mRequest, confirmedFrom);
    }

    @Override
    @Transactional
    public MRequest update(MRequest request, Date rentFrom) throws InvalidException, MRequestAlreadyExistsException, ForbiddenException {
        this.validateRequest(request);
        request = this.requestRepo.save(request);

        // Create a History if request is marked as confirmed
        if (request.isMarkedAsConfirmed()) {
            if (rentFrom == null)
                throw new InvalidException("You have to provide a confirmed from date if you mark this request confirmed!");
            RentHistory history = new RentHistory(request.getRequestedBy(), request.getApartment(), rentFrom);
            this.historyService.save(history);
            // set apartment availability false
            Apartment apartment = request.getApartment();
            apartment.setAvailable(false);
            this.apartmentService.save(apartment);
        }
        return request;
    }

    private void validateRequest(MRequest request) throws InvalidException, MRequestAlreadyExistsException, ForbiddenException {
        if (request == null) throw new InvalidException("Request can not be null!");
//        if (!request.hasAuthorizedUser(WebSecurityConfig.getCurrentUser()))
//            throw new ForbiddenException("You are forbidden to perform this action.");
        if (request.getRequestedTo() == null && request.getApartment() == null)
            throw new InvalidException("Invalid request. Apartment: " + request.getApartment());
        if (request.getRequestedBy() == null)
            throw new InvalidException("Invalid request. RequestedBy: " + request.getRequestedBy());
        if (request.getStatus() == null) request.setStatus(MRequest.Status.PENDING);
        // check if user has already requested for that apartment
        if (request.getId() == null) {
            // if alredy requested for that apartment
            if (request.getRequestedTo() == null && this.requestRepo.existsByApartmentIdAndRequestedByIdAndStatus(request.getApartment().getId(), request.getRequestedBy().getId(), MRequest.Status.PENDING.getValue()))
                throw new MRequestAlreadyExistsException("You can not send multiple request for same apartment!");
            if (request.getRequestedTo() != null && this.requestRepo.existsByRequestedToIdAndRequestedByIdAndStatus(request.getRequestedTo().getId(), request.getRequestedBy().getId(), MRequest.Status.PENDING.getValue()))
                throw new MRequestAlreadyExistsException("You can not send multiple request to same person!");
            // if requested more than 3 times in a month
            Map<DateUtil.DateRangeType, Calendar> dateRange = DateUtil.buildDateRange(DateUtil.Period.THIS_MONTH);
            Date fromDate = dateRange.get(DateUtil.DateRangeType.DATE_FROM).getTime();
            Date toDate = dateRange.get(DateUtil.DateRangeType.DATE_TO).getTime();
            int requestCount = this.requestRepo.countByRequestedByIdAndCreatedBetween(request.getRequestedBy().getId(), fromDate, toDate);
            // TODO: Fetch request limit from ServiceCharges entity
            if (requestCount >= this.requestLimit)
                throw new ForbiddenException("You can not send more than 3 requests in a month.");
        }
    }

//    @Override
//    public void changeStatus(Long requestId, String status, Long assignedEmployeeId) throws MRequestAlreadyExistsException, MRequestNotFoundException, UserNotFoundException, ForbiddenException, InvalidException {
//        MRequest mRequest = this.getOne(requestId);
//        User assignedEmployee = this.userService.findOne(assignedEmployeeId);
//        if (!assignedEmployee.isFieldEmployee())
//            throw new InvalidException("Can not assign a user if it's not field employee");
//        MRequest.Status requestStatus = MRequest.Status.fromValue(status);
//        mRequest.setStatus(requestStatus);
//        mRequest.setAssignedEmployee(assignedEmployee);
//        this.requestRepo.save(mRequest);
//    }

    @Override
    public boolean exists(Long id) {
        return this.requestRepo.existsById(id);
    }

    @Override
    @Transactional
    public MRequest fixAppointment(Long id, String status, Long employeeId, Date appointmentDate, String appointmentPlace) throws ForbiddenException, InvalidException, MRequestAlreadyExistsException, NullException, MRequestNotFoundException, UserNotFoundException {

        if (!MRequest.Status.APPOINTMENT_FIXED.equals(MRequest.Status.fromValue(status)))
            throw new InvalidException("Appointment can not be set for status " + status);
        if (appointmentDate == null || DateUtil.isPast(appointmentDate))
            throw new ForbiddenException("Appointment date invalid!");
        MRequest request = this.changeStatus(id, status, employeeId, null);
        Appointment appointment = request.getAppointment();
        if (appointment == null) appointment = new Appointment();
        appointment.setDate(appointmentDate);
        appointment.setPlace(appointmentPlace);
        appointment.setRequest(request);
        request.setAppointment(this.appointmentService.save(appointment));

        return this.update(request, null);
    }

    @Override
    public Page<MRequest> findByStatusAndUser(String status, String userType, String username, Integer page) {
        if (status == null) status = "";
        if (userType == null) userType = "";
        if (username == null) username = "";
        if (status.isEmpty() && username.isEmpty()) return this.getAll(page);
        if (!status.isEmpty() && "User".equalsIgnoreCase(userType))
            return requestRepo.findByStatusAndRequestedByUsername(status, username, PageAttr.getPageRequest(page));
        if (!status.isEmpty() && "Landlord".equalsIgnoreCase(userType))
            return requestRepo.findByStatusAndApartmentBuildingLandlordUsername(status, username, PageAttr.getPageRequest(page));
        if (status.isEmpty() && "User".equalsIgnoreCase(userType))
            return requestRepo.findByRequestedByUsername(username, PageAttr.getPageRequest(page));
        if (status.isEmpty() && "Landlord".equalsIgnoreCase(userType))
            return requestRepo.findByApartmentBuildingLandlordUsername(username, PageAttr.getPageRequest(page));
        if (userType.isEmpty()) return this.findByStatus(status, page);
        return null;
    }

}
