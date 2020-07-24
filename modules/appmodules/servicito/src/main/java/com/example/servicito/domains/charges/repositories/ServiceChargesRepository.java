package com.example.servicito.domains.charges.repositories;

import com.example.servicito.domains.charges.models.entities.ServiceCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceChargesRepository extends JpaRepository<ServiceCharges, Long> {
}
