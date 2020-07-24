package com.example.servicito.domains.charges.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.coreweb.domains.base.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "service_charges")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ServiceCharges extends BaseEntity {
    private int buildingInfoCollectionCharge;
    private int apartmentInfoCollectionCharge;
    private int fieldPersonRentCommission;
    private int rentServiceChargeForCompany;
    private int miscServiceCharge;

    public int getBuildingInfoCollectionCharge() {
        return buildingInfoCollectionCharge;
    }

    public void setBuildingInfoCollectionCharge(int buildingInfoCollectionCharge) {
        this.buildingInfoCollectionCharge = buildingInfoCollectionCharge;
    }

    public int getApartmentInfoCollectionCharge() {
        return apartmentInfoCollectionCharge;
    }

    public void setApartmentInfoCollectionCharge(int apartmentInfoCollectionCharge) {
        this.apartmentInfoCollectionCharge = apartmentInfoCollectionCharge;
    }

    public int getFieldPersonRentCommission() {
        return fieldPersonRentCommission;
    }

    public void setFieldPersonRentCommission(int fieldPersonRentCommission) {
        this.fieldPersonRentCommission = fieldPersonRentCommission;
    }

    public int getRentServiceChargeForCompany() {
        return rentServiceChargeForCompany;
    }

    public void setRentServiceChargeForCompany(int rentServiceChargeForCompany) {
        this.rentServiceChargeForCompany = rentServiceChargeForCompany;
    }

    public int getMiscServiceCharge() {
        return miscServiceCharge;
    }

    public void setMiscServiceCharge(int miscServiceCharge) {
        this.miscServiceCharge = miscServiceCharge;
    }
}
