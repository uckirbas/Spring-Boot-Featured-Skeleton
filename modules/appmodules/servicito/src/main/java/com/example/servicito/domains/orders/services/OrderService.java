package com.example.servicito.domains.orders.services;

import com.example.servicito.domains.orders.models.entities.ServiceOrder;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface OrderService {
    ServiceOrder save(ServiceOrder order) throws NotFoundException, InvalidException;

    Page<ServiceOrder> getAll(int page);

    Page<ServiceOrder> getAll(String status, int page) throws InvalidException;

    Page<ServiceOrder> getAll(String status, String serviceType, int page) throws InvalidException;

    Page<ServiceOrder> getAll(String status, Date fromDate, Date toDate, int page, int size) throws InvalidException;

    Page<ServiceOrder> getAll(String status, String serviceType, Date fromDate, Date toDate, int page, int size) throws InvalidException;

    Page<ServiceOrder> getAll(Long userId, String status, Date fromDate, Date toDate, int page, int size) throws InvalidException;

    Page<ServiceOrder> getAll(Long userId, String status, String serviceType, Date fromDate, Date toDate, int page, int size) throws InvalidException;

    ServiceOrder findOne(Long id) throws NotFoundException;

    ServiceOrder changeOrderStatus(Long orderId, String status, int charge, Long assignedEmployeeId) throws NotFoundException, UserNotFoundException, ForbiddenException, InvalidException;

    ServiceOrder findByPhone(String phone) throws NotFoundException;

    Page<ServiceOrder> findByUser(Long userId, int page) throws NotFoundException;

    boolean isLimitExceeded(Date fromDate, Date toDate, String phone);
}
