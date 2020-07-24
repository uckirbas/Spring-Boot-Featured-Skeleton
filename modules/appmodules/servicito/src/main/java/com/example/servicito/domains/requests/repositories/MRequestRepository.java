package com.example.servicito.domains.requests.repositories;

import com.example.servicito.domains.requests.models.entities.MRequest;
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
public interface MRequestRepository extends JpaRepository<MRequest, Long> {
    Page<MRequest> findByStatus(String status, Pageable pageable);
    Page<MRequest> findByStatusAndRequestedByUsername(String status, String username, Pageable pageable);
    Page<MRequest> findByStatusAndApartmentBuildingLandlordUsername(String status, String username, Pageable pageable);
    Page<MRequest> findByRequestedByUsername(String username, Pageable pageable);
    Page<MRequest> findByApartmentBuildingLandlordUsername(String username, Pageable pageable);
    Page<MRequest> findByRequestedById(Long userId, Pageable pageable);
    Page<MRequest> findByAssignedEmployeeId(Long userId, Pageable pageable);
    Page<MRequest> findByRequestedToId(Long userId, Pageable pageable);
    Page<MRequest> findByApartmentBuildingLandlordId(Long userId, Pageable pageable);
    boolean existsByApartmentIdAndRequestedByIdAndStatus(Long apartmentId,Long requestedById,String status);
    boolean existsByRequestedToIdAndRequestedByIdAndStatus(Long requestedTo,Long requestedById,String status);
    int countByApartmentBuildingLandlordIdAndCreatedBetween(Long userId, Date fromDate, Date toDate);

    int countByAssignedEmployeeUsernameAndStatusAndLastUpdatedBetween(String username, String status, Date fromDate, Date toDate);

    int countByRequestedByIdAndCreatedBetween(Long id, Date fromDate, Date toDate);

    int countByCreatedBetween(Date fromDate, Date toDate);

    int countByStatusAndCreatedBetween(String status, Date fromDate, Date toDate);

    @Query("SELECT new com.servicito.webservice.domains.statistics.models.dtos.DateCountPair(u.created, count(u.id)) FROM MRequest u WHERE u.created BETWEEN :fromDate AND :toDate")
    List<DateCountPair> countRequestsInPeriod(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
