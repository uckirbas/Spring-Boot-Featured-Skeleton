package com.example.servicito.domains.charges.services;

import com.example.common.exceptions.notfound.NotFoundException;
import com.example.coreweb.utils.PageAttr;
import com.example.servicito.domains.charges.models.entities.ServiceCharges;
import com.example.servicito.domains.charges.repositories.ServiceChargesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceChargesServiceImpl implements ServiceChargesService {

    private final ServiceChargesRepository serviceChargesRepository;

    @Autowired
    public ServiceChargesServiceImpl(ServiceChargesRepository serviceChargesRepository) {
        this.serviceChargesRepository = serviceChargesRepository;
    }

    @Override
    public ServiceCharges saveServiceCharges(ServiceCharges serviceCharges) throws NotFoundException {
        if (serviceCharges == null)
            throw new IllegalArgumentException("apartment can not be null!");
        if (serviceCharges.getId() != null && !this.isExists(serviceCharges.getId()))
            throw new NotFoundException("Could not update ServiceCharges.");
        return this.serviceChargesRepository.save(serviceCharges);
    }

    @Override
    public ServiceCharges getOneServiceCharges(Long id) throws NotFoundException {
        if (id==null || !this.isExists(id))
            throw new NotFoundException("Could not update ServiceCharges.");
        return this.serviceChargesRepository.getOne(id);
    }

    @Override
    public List<ServiceCharges> getAllServiceCharges() {
        return this.serviceChargesRepository.findAll();
    }

    @Override
    public Page<ServiceCharges> getAllServiceChargesPaginated(int pageNumber) {
        return this.serviceChargesRepository.findAll(PageAttr.getPageRequest(pageNumber));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (id==null || !this.isExists(id))
            throw new NotFoundException("Could not found ServiceCharges.");
        this.serviceChargesRepository.deleteById(id);
    }

    @Override
    public Long countAllServiceCharges() {
        return this.serviceChargesRepository.count();
    }

    @Override
    public boolean isExists(Long id) {
        return this.serviceChargesRepository.existsById(id);
    }
}
