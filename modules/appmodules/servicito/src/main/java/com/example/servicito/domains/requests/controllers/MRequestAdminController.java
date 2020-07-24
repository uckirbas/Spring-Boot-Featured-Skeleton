package com.example.servicito.domains.requests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.servicito.domains.notifications.services.NotificationService;
import com.example.servicito.domains.requests.models.entities.MRequest;
import com.example.servicito.domains.requests.services.MRequestService;
import com.example.common.exceptions.exists.MRequestAlreadyExistsException;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/admin/requests")
public class MRequestAdminController {

    private final MRequestService requestService;
    private final NotificationService notificationService;

    @Autowired
    public MRequestAdminController(MRequestService requestService, NotificationService notificationService) {
        this.requestService = requestService;
        this.notificationService = notificationService;
    }

    @PostMapping("/{id}/changeStatus")
    private ResponseEntity changeStatus(@PathVariable("id") Long id,
                                        @RequestParam("status") String status,
                                        @RequestParam(value = "assignedEmployeeId", required = false) Long assignedEmployeeId,
                                        @RequestParam(value = "confirmFrom", required = false) Date confirmFrom) throws ForbiddenException, InvalidException, MRequestAlreadyExistsException, NotFoundException, JsonProcessingException, UnknownException {
        MRequest request = this.requestService.changeStatus(id, status, assignedEmployeeId, confirmFrom);
        // notify users about request status change
        this.notificationService.sendNotificationsByRequestStatus(request);
        return ResponseEntity.ok(request);
    }

    @GetMapping("")
    private ResponseEntity getAllRequests(@RequestParam(value = "status", required = false) String status,
                                          @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Page<MRequest> requests;
        if (status != null)
            requests = this.requestService.findByStatus(status, Math.abs(page));
        else requests = this.requestService.getAll(Math.abs(page));

        return ResponseEntity.ok(requests);
    }

}
