package com.example.servicito.domains.orders.controllers;

import com.example.servicito.domains.orders.models.entities.ServiceOrder;
import com.example.servicito.domains.orders.services.OrderService;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/api/v1/admin/orders")
public class OrderAdminController {
    private final OrderService orderService;

    @Autowired
    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    private ResponseEntity getAll(@RequestParam("status") String status,
                                  @RequestParam(value = "serviceType", required = false) String serviceType,
                                  @RequestParam(value = "fromDate", required = false) Date fromDate,
                                  @RequestParam(value = "toDate", required = false) Date toDate,
                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size) throws InvalidException {

        Page<ServiceOrder> orderPage;
        System.out.println("Admin controller:" + serviceType);
        if (serviceType != null) {
            orderPage = this.orderService.getAll(status, serviceType, fromDate, toDate, page, size);
        } else {
            orderPage = this.orderService.getAll(status, fromDate, toDate, page, size);
        }

        System.out.println(orderPage.getContent().toString());
        return ResponseEntity.ok(orderPage);
    }

    @PatchMapping("/{orderId}/changeStatus")
    private ResponseEntity changeStatus(@PathVariable("orderId") Long orderId,
                                        @RequestParam("status") String status,
                                        @RequestParam("charge") Integer charge,
                                        @RequestParam("assignedEmployeeId") Long assignedEmployeeId) throws NotFoundException, UserNotFoundException, ForbiddenException, InvalidException {
        ServiceOrder order = this.orderService.changeOrderStatus(orderId, status, charge, assignedEmployeeId);
        return ResponseEntity.ok(order);
    }


}
