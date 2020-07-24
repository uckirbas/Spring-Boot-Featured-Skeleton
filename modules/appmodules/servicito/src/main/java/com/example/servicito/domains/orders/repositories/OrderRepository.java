package com.example.servicito.domains.orders.repositories;

import com.example.servicito.domains.orders.models.entities.ServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ServiceOrder, Long> {
    Page<ServiceOrder> findByUserId(Long userId, Pageable pageable);

    Page<ServiceOrder> findByStatus(String statue, Pageable pageable);

    Page<ServiceOrder> findByStatusAndServiceType(String statue, String serviceType, Pageable pageable);

    Page<ServiceOrder> findByStatusAndCreatedBetween(String statue, Date fromDate, Date toDate, Pageable pageable);

    Page<ServiceOrder> findByStatusAndServiceTypeAndCreatedBetween(String statue, String serviceType, Date fromDate, Date toDate, Pageable pageable);

    Page<ServiceOrder> findByUserIdAndStatus(Long userId, String statue, Pageable pageable);

    Page<ServiceOrder> findByUserIdAndStatusAndCreatedBetween(Long userId, String statue, Date fromDate, Date toDate, Pageable pageable);

    Page<ServiceOrder> findByUserIdAndStatusAndServiceTypeAndCreatedBetween(Long userId, String statue, String serviceType, Date fromDate, Date toDate, Pageable pageable);

    ServiceOrder findByPhoneNumber(String phone);

    int countByCreatedBetween(Date fromDate, Date toDate);

    List<ServiceOrder> findByPhoneNumberAndCreatedBetween(String phone, Date fromDate, Date toDate);

}
