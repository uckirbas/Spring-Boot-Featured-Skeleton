package com.example.servicito.domains.complains.repositories;

import com.example.servicito.domains.complains.models.entities.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Long> {

    int countByApartmentBuildingLandlordIdAndCreatedBetween(Long userId, Date fromDate, Date toDate);
}
