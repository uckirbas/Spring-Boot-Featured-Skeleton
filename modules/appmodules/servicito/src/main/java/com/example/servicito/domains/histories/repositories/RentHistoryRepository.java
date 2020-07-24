package com.example.servicito.domains.histories.repositories;

import com.example.servicito.domains.histories.models.entities.RentHistory;
import com.example.servicito.domains.statistics.models.dtos.DateCountPair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RentHistoryRepository extends JpaRepository<RentHistory,Long> {
    Page<RentHistory> findByUserId(Long userId, Pageable pageable);
    Page<RentHistory> findByApartmentId(Long apartmentId, Pageable pageable);

    RentHistory findFirstByUserIdOrderByIdDesc(Long userId);
    RentHistory findFirstByApartmentIdOrderByIdDesc(Long apartmentId);


    @Query("SELECT new com.servicito.webservice.domains.statistics.models.dtos.DateCountPair(u.created, count(u.id)) FROM RentHistory u WHERE u.created BETWEEN :fromDate AND :toDate")
    List<DateCountPair> countHistoriesInPeriod(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
