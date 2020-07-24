package com.example.servicito.domains.orders.services;

import com.example.coreweb.utils.PageAttr;
import com.example.common.utils.DateUtil;
import com.example.servicito.domains.orders.models.entities.ServiceOrder;
import com.example.servicito.domains.orders.repositories.OrderRepository;
import com.example.auth.entities.User;
import com.example.acl.domains.users.services.UserService;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo, UserService userService) {
        this.orderRepo = orderRepo;
        this.userService = userService;
    }


    @Override
    public ServiceOrder save(ServiceOrder order) throws NotFoundException, InvalidException {
        if (order == null) throw new NotFoundException("Order can not be null");
        if (order.getPhoneNumber()==null) throw new InvalidException("Phone number isn't provided");
        if (order.getId() == null) order.setStatus(ServiceOrder.Status.RECEIVED.toString().toLowerCase());
        return this.orderRepo.save(order);
    }

    @Override
    public Page<ServiceOrder> getAll(int page) {
        return this.orderRepo.findAll(PageAttr.getPageRequest(page));
    }

    @Override
    public Page<ServiceOrder> getAll(String status, int page) throws InvalidException {
        if (status == null) throw new InvalidException("Status can not be null");
        return this.orderRepo.findByStatus(status, PageAttr.getPageRequest(page));
    }

    @Override
    public Page<ServiceOrder> getAll(String status, String serviceType, int page) throws InvalidException {
        if (status == null || serviceType == null) throw new InvalidException("Status or service type can not be null");
        return this.orderRepo.findByStatusAndServiceType(status, serviceType, PageAttr.getPageRequest(page));
    }

    @Override
    public Page<ServiceOrder> getAll(String status, Date fromDate, Date toDate, int page, int size) throws InvalidException {
        if (status == null) throw new InvalidException("Status can not be null");
        if (fromDate == null || toDate == null)
            return getAll(status, page);
        fromDate = DateUtil.getDayStart(fromDate);
        toDate = DateUtil.getDayEnd(toDate);
        return this.orderRepo.findByStatusAndCreatedBetween(status, fromDate, toDate, PageAttr.getPageRequest(page, size));
    }

    @Override
    public Page<ServiceOrder> getAll(String status, String serviceType, Date fromDate, Date toDate, int page, int size) throws InvalidException {
        System.out.println("Service : " + serviceType);
        if (status == null || serviceType == null) throw new InvalidException("Status or service type can not be null");
        if (fromDate == null || toDate == null)
            return getAll(status, serviceType, page);
        fromDate = DateUtil.getDayStart(fromDate);
        toDate = DateUtil.getDayEnd(toDate);
        return this.orderRepo.findByStatusAndServiceTypeAndCreatedBetween(status, serviceType, fromDate, toDate, PageAttr.getPageRequest(page, size));
    }

    @Override
    public Page<ServiceOrder> getAll(Long userId, String status, Date fromDate, Date toDate, int page, int size) throws InvalidException {
        if (status == null) throw new InvalidException("Status can not be null");
        if (fromDate == null || toDate == null)
            return getAll(status, page);
        fromDate = DateUtil.getDayStart(fromDate);
        toDate = DateUtil.getDayEnd(toDate);
        return this.orderRepo.findByUserIdAndStatusAndCreatedBetween(userId, status, fromDate, toDate, PageAttr.getPageRequest(page, size));
    }

    @Override
    public Page<ServiceOrder> getAll(Long userId, String status, String serviceType, Date fromDate, Date toDate, int page, int size) throws InvalidException {
        if (status == null || serviceType == null) throw new InvalidException("Status or service type can not be null");
        if (fromDate == null || toDate == null)
            return getAll(status, serviceType, page);
        fromDate = DateUtil.getDayStart(fromDate);
        toDate = DateUtil.getDayEnd(toDate);
        return this.orderRepo.findByUserIdAndStatusAndServiceTypeAndCreatedBetween(userId, status, serviceType, fromDate, toDate, PageAttr.getPageRequest(page, size));
    }

    @Override
    public ServiceOrder findOne(Long id) throws NotFoundException {
        ServiceOrder order = this.orderRepo.findById(id).orElse(null);
        if (order == null) throw new NotFoundException("Could not found order with id: " + id);
        return order;
    }

    @Override
    public ServiceOrder changeOrderStatus(Long orderId, String status, int charge, Long assignedEmployeeId) throws NotFoundException, UserNotFoundException, ForbiddenException, InvalidException {
        if (status == null) throw new ForbiddenException("Status can not be null");
        ServiceOrder order = this.findOne(orderId);
        order.setStatus(status);
        order.setCharge(charge);
        if (order.equalsStatus(ServiceOrder.Status.PROCESSING)) {
            User user = this.userService.find(assignedEmployeeId).orElseThrow(()->new ForbiddenException("An employee needs to be assigned to process this request."));
            order.setUser(user);
        }

        return this.save(order);
    }

    @Override
    public ServiceOrder findByPhone(String phone) throws NotFoundException {
        ServiceOrder order = this.orderRepo.findByPhoneNumber(phone);
        if (order == null) throw new NotFoundException("Could not found order with phone: " + phone);
        return order;
    }

    @Override
    public Page<ServiceOrder> findByUser(Long userId, int page) throws NotFoundException {
        Page<ServiceOrder> order = this.orderRepo.findByUserId(userId, PageAttr.getPageRequest(page));
        if (order == null) throw new NotFoundException("Could not found order for userId: " + userId);
        return order;
    }

    @Override
    public boolean isLimitExceeded(Date fromDate, Date toDate, String phone) {
        List<ServiceOrder> orders = this.orderRepo.findByPhoneNumberAndCreatedBetween(phone, fromDate, toDate);
        return orders != null && orders.size() >= 2;
    }
}
