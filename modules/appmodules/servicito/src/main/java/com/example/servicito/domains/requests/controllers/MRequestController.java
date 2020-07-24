package com.example.servicito.domains.requests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.apartments.services.ApartmentService;
import com.example.auth.annotations.CurrentUser;
import com.example.servicito.domains.notifications.services.NotificationService;
import com.example.servicito.domains.requests.models.entities.MRequest;
import com.example.servicito.domains.requests.services.MRequestService;
import com.example.auth.entities.User;
import com.example.acl.domains.users.services.UserService;
import com.example.common.exceptions.exists.MRequestAlreadyExistsException;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.ApartmentNotFoundException;
import com.example.common.exceptions.notfound.MRequestNotFoundException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import com.example.common.exceptions.nullpointer.NullException;
import com.example.common.exceptions.unknown.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/requests")
public class MRequestController {
    private final MRequestService mRequestService;
    private final ApartmentService apartmentService;
    private final NotificationService notificationService;
    private final UserService userService;


    @Value("${app.config.landlordownagent}")
    private boolean isLandlordOwnAgent;

    @Autowired
    public MRequestController(MRequestService mRequestService, ApartmentService apartmentService, NotificationService notificationService, UserService userService) {
        this.mRequestService = mRequestService;
        this.apartmentService = apartmentService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("")
    private ResponseEntity allByUser(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @CurrentUser User currentUser) {
        Page<MRequest> mRequestPage = this.mRequestService.getAllByUser(currentUser.getId(), Math.abs(page));
        return ResponseEntity.ok(mRequestPage);
    }

    @GetMapping("/forMe")
    private ResponseEntity getAllRequestsForMe(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                               @CurrentUser User currentUser) {
        Page<MRequest> mRequestPage = this.mRequestService.getAllByAssignedEmployee(currentUser.getId(), Math.abs(page));
        return ResponseEntity.ok(mRequestPage);
    }

    @GetMapping("/toMe")
    private ResponseEntity getAllRequestsToMe(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @CurrentUser User currentUser) {
        Page<MRequest> mRequestPage = this.mRequestService.getAllByRequestedTo(currentUser.getId(), Math.abs(page));
        return ResponseEntity.ok(mRequestPage);
    }

    @PostMapping("")
    private ResponseEntity create(@ModelAttribute MRequest mRequest,
                                  @RequestParam(value = "apartmentId", required = false) Long apartmentId,
                                  @RequestParam(value = "requestedTo", required = false) Long requestedTo,
                                  @CurrentUser User currentUser) throws NotFoundException, InvalidException, MRequestAlreadyExistsException, ForbiddenException, JsonProcessingException, UnknownException {
        System.out.println(mRequest.getNote());
        mRequest.setId(null);
        if (apartmentId != null) {
            Apartment apartment = this.apartmentService.find(apartmentId).orElseThrow(() -> new ApartmentNotFoundException("Couldn't find apartment with id:" + apartmentId));
            mRequest.setApartment(apartment);
        }
        if (requestedTo != null) {
            User rToUser = this.userService.find(requestedTo).orElseThrow(() -> new UsernameNotFoundException("Could not find user with id: " + requestedTo));
            mRequest.setRequestedTo(rToUser);
        }
        mRequest.setRequestedBy(currentUser);
        mRequest = this.mRequestService.save(mRequest);
        if (this.isLandlordOwnAgent)
            this.notificationService.sendNotificationsByRequestStatus(mRequest);
        return ResponseEntity.ok(mRequest);
    }

    @GetMapping("/{id}")
    private ResponseEntity details(@PathVariable("id") Long id) throws MRequestNotFoundException, ForbiddenException {
        MRequest mRequest = this.mRequestService.getOne(id);
        return ResponseEntity.ok(mRequest);
    }

    /**
     * Method to change Request Status
     *
     * @param id
     * @param status
     * @param confirmFrom needed only when request is sent to confirm the rent
     * @param currentUser
     * @return
     * @throws UserNotFoundException
     * @throws MRequestAlreadyExistsException
     * @throws MRequestNotFoundException
     * @throws ForbiddenException
     * @throws InvalidException
     */
    @PatchMapping("/{id}/changeStatus")
    private ResponseEntity changeStatus(@PathVariable("id") Long id,
                                        @RequestParam("status") String status,
                                        @RequestParam(value = "confirmFrom", required = false) Date confirmFrom,
                                        @RequestParam(value = "appointmentDate", required = false) Date appointmentDate,
                                        @RequestParam(value = "appointmentPlace", required = false) String appointmentPlace,
                                        @CurrentUser User currentUser) throws NotFoundException, MRequestAlreadyExistsException, ForbiddenException, InvalidException, NullException, UnknownException, JsonProcessingException {
        MRequest request;
        if (MRequest.Status.APPOINTMENT_FIXED.equals(MRequest.Status.fromValue(status)))
            request = this.mRequestService.fixAppointment(id, status, currentUser.getId(), appointmentDate, appointmentPlace);
        else
            request = this.mRequestService.changeStatus(id, status, currentUser.getId(), confirmFrom);

        // notify users about request status change
        this.notificationService.sendNotificationsByRequestStatus(request);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/assignMe")
    private ResponseEntity changeStatus(@PathVariable("id") Long id,
                                        @CurrentUser User currentUser) throws ForbiddenException, InvalidException, MRequestAlreadyExistsException, NotFoundException, JsonProcessingException, UnknownException {
        if (!this.isLandlordOwnAgent) throw new ForbiddenException("You are not authorized to assign yourself");
        MRequest request = this.mRequestService.changeStatus(id, MRequest.Status.RECEIVED.getValue(), currentUser.getId(), null);
        // notify users about request status change
        this.notificationService.sendNotificationsByRequestStatus(request);
        return ResponseEntity.ok(request);
    }

    @PatchMapping("/cancel/{requestId}")
    private ResponseEntity cancelRequest(@PathVariable("requestId") Long requestId) throws NotFoundException, InvalidException, ForbiddenException, MRequestAlreadyExistsException, JsonProcessingException, UnknownException {
        MRequest request = this.mRequestService.changeStatus(requestId, MRequest.Status.CANCELED.getValue(), null, null);
        // notify users
        this.notificationService.sendNotificationsByRequestStatus(request);

        return ResponseEntity.ok().build();
    }

}
