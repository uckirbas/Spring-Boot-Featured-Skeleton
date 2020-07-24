package com.example.servicito.domains.charges.models.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Charge {
    private int gasCharge;
    private int waterCharge;
    private int electricityCharge;
    private int securityCharge;
    @NotNull
    @Column(nullable = false)
    private int rent;
    private int advance;

    @JsonIgnore
    public int getTotalUtilityCharges() {
        int charge = 0;
        if (getWaterCharge() > 0) charge += getWaterCharge();
        if (getGasCharge() > 0) charge += getGasCharge();
        if (getElectricityCharge() > 0) charge += getElectricityCharge();
        if (getSecurityCharge() > 0) charge += getSecurityCharge();
        return charge;
    }

    @JsonIgnore
    public int getTotalCharges() {
        return this.getRent() + this.getTotalUtilityCharges();
    }

    @JsonIgnore
    public String getWaterChargeStr() {
        if (this.waterCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*";
        if (this.waterCharge == 0) return "*ভাড়ার অন্তর্ভূক্ত*";
        return this.waterCharge + " টাকা";
    }

    @JsonIgnore
    public String getGasChargeStr() {
        if (this.gasCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*";
        if (this.gasCharge == 0) return "*ভাড়ার অন্তর্ভূক্ত*";
        return this.gasCharge + " টাকা";
    }

    @JsonIgnore
    public String getElectricityChargeStr() {
        if (this.electricityCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*";
        if (this.electricityCharge == 0) return "*ভাড়ার অন্তর্ভূক্ত*";
        return this.electricityCharge + " টাকা";
    }

    @JsonIgnore
    public String getSecurityChargeStr() {
        if (this.securityCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*";
        if (this.securityCharge == 0) return "*ভাড়ার অন্তর্ভূক্ত*";
        return this.securityCharge + " টাকা";
    }


    public int getGasCharge() {
        return gasCharge;
    }

    public void setGasCharge(int gasCharge) {
        this.gasCharge = gasCharge;
    }

    public int getWaterCharge() {
        return waterCharge;
    }

    public void setWaterCharge(int waterCharge) {
        this.waterCharge = waterCharge;
    }

    public int getElectricityCharge() {
        return electricityCharge;
    }

    public void setElectricityCharge(int electricityCharge) {
        this.electricityCharge = electricityCharge;
    }

    public int getSecurityCharge() {
        return securityCharge;
    }

    public void setSecurityCharge(int securityCharge) {
        this.securityCharge = securityCharge;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getAdvance() {
        return advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }
}
