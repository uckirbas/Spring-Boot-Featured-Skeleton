package com.example.servicito.domains.charges.services;

import com.example.common.exceptions.notfound.NotFoundException;
import com.example.servicito.domains.charges.models.entities.ServiceCharges;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ServiceChargesService {

    ServiceCharges saveServiceCharges(ServiceCharges serviceCharges) throws NotFoundException;

    ServiceCharges getOneServiceCharges(Long id) throws NotFoundException;

    List<ServiceCharges> getAllServiceCharges();

    Page<ServiceCharges> getAllServiceChargesPaginated(int pageNumber);

    void delete(Long id) throws NotFoundException;

    Long countAllServiceCharges();

    boolean isExists(Long id);

}
