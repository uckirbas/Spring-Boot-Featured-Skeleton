package com.example.servicito.domains.orders.controllers;

import com.example.auth.utils.NetworkUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.common.utils.DateUtil;
import com.example.auth.annotations.CurrentUser;
import com.example.servicito.domains.notifications.models.pojo.NotificationData;
import com.example.servicito.domains.notifications.models.pojo.PushNotification;
import com.example.servicito.domains.notifications.services.NotificationService;
import com.example.servicito.domains.orders.models.entities.ServiceOrder;
import com.example.servicito.domains.orders.services.OrderAttemptService;
import com.example.servicito.domains.orders.services.OrderService;
import com.example.auth.entities.User;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.limitExceed.LimitExceedException;
import com.example.common.exceptions.unknown.UnknownException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final NotificationService notificationService;
    private final OrderAttemptService orderAttemptService;

    @Autowired
    public OrderController(OrderService orderService, NotificationService notificationService, OrderAttemptService orderAttemptService) {
        this.orderService = orderService;
        this.notificationService = notificationService;
        this.orderAttemptService = orderAttemptService;
    }

    @GetMapping("")
    private ResponseEntity getMyOrders(@RequestParam("status") String status,
                                       @RequestParam(value = "serviceType", required = false) String serviceType,
                                       @RequestParam(value = "fromDate", required = false) Date fromDate,
                                       @RequestParam(value = "toDate", required = false) Date toDate,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                       @CurrentUser User currentUser) throws InvalidException {
        Page<ServiceOrder> orderPage;
        System.out.println(serviceType);
        if(serviceType!=null){
            orderPage = this.orderService.getAll(currentUser.getId(), status, serviceType, fromDate, toDate, page, size);
        }else {
            orderPage = this.orderService.getAll(currentUser.getId(), status, fromDate, toDate, page, size);
        }
        return ResponseEntity.ok(orderPage);
    }

    @GetMapping("/{id}")
    private ResponseEntity getOrder(@PathVariable("id") Long orderId,
                                    @CurrentUser User currentUser) throws NotFoundException, ForbiddenException {
        ServiceOrder order = this.orderService.findOne(orderId);
        if (!order.hasAuthorizedAccessFor(currentUser)) throw new ForbiddenException("Access denied!");
        return ResponseEntity.ok(order);
    }

    @PostMapping("/create")
    private ResponseEntity create(@RequestBody ServiceOrder serviceOrder,
                                  @CurrentUser User currentUser) throws NotFoundException, InvalidException {

        // flood control
        String ip = NetworkUtil.getClientIP();
        if (this.orderAttemptService.isBlocked(ip))
            throw new LimitExceedException("We're sorry but you have reached your maximum order limit. Please contact us to place order manually.");
        if (this.orderService.isLimitExceeded(DateUtil.getDayStart(new Date()), DateUtil.getDayEnd(new Date()), serviceOrder.getPhoneNumber()))
            throw new LimitExceedException("We're sorry but you have reached your maximum order limit. Please contact us to place order manually.");

        serviceOrder.setUser(currentUser);
        serviceOrder = this.orderService.save(serviceOrder);
        // send push to admin
        this.notifyAdmin(serviceOrder);
        // flood control
        this.orderAttemptService.orderSuccess(ip);
        return ResponseEntity.ok(serviceOrder);
    }

    private void notifyAdmin(ServiceOrder order) {
        new Thread(() -> {
            NotificationData data = new NotificationData();
            String from = order.getUser() != null ? order.getUser().getUsername() : order.getPhoneNumber();
            data.setTitle("New " + order.getServiceType() + " order from: " + from);
            String description = "F:" + order.getServiceFrom() + " | T:" + order.getServiceLocation() + " | " + DateUtil.getReadableDateTime(order.getDate());
            String brief = description.substring(0, Math.min(description.length(), 100));
            data.setMessage(brief);
            data.setType(PushNotification.Type.ADMIN_NOTIFICATIONS.getValue());

            PushNotification notification = new PushNotification(null, data);
            notification.setTo("/topics/adminnotifications");
            try {
                notificationService.sendNotification(notification);
            } catch (InvalidException | JsonProcessingException | UnknownException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
