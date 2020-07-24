package com.example.servicito.domains.charges.controllers;

import com.example.common.exceptions.notfound.NotFoundException;
import com.example.servicito.domains.charges.models.entities.ServiceCharges;
import com.example.servicito.domains.charges.services.ServiceChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/serviceCharges")
public class ServiceChargesController {

    private final ServiceChargesService serviceChargesService;

    @Autowired
    public ServiceChargesController(ServiceChargesService serviceChargesService) {
        this.serviceChargesService = serviceChargesService;
    }

    @GetMapping("")
    private ResponseEntity getServiceChargesList(@RequestParam(value = "page",defaultValue = "0") Integer page){
        Page serviceChargesList = this.serviceChargesService.getAllServiceChargesPaginated(page);
        return ResponseEntity.ok(serviceChargesList);
    }

    @PostMapping("")
    private ResponseEntity<ServiceCharges> saveServiceCharges(@RequestBody ServiceCharges serviceCharges) throws NotFoundException {
        serviceCharges = this.serviceChargesService.saveServiceCharges(serviceCharges);
        return ResponseEntity.ok(serviceCharges);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ServiceCharges> getOneServiceCharges(@PathVariable("id") Long id) throws NotFoundException {
        ServiceCharges serviceCharges = this.serviceChargesService.getOneServiceCharges(id);
        return ResponseEntity.ok(serviceCharges);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ServiceCharges> updateApartment(@RequestBody ServiceCharges serviceCharges,
                                                           @PathVariable("id") Long id) throws NotFoundException {
        serviceCharges.setId(id);
        serviceCharges = this.serviceChargesService.saveServiceCharges(serviceCharges);
        return ResponseEntity.ok(serviceCharges);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteApartment(@PathVariable("id") Long id) throws NotFoundException {
        this.serviceChargesService.delete(id);
        return ResponseEntity.ok().build();
    }


}
