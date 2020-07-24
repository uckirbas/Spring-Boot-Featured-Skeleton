package com.example.servicito.domains.apartments.models.embedables

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Charge {
    @Column(name = "gas_charge")
    var gasCharge = 0

    @Column(name = "water_charge")
    var waterCharge = 0

    @Column(name = "electricity_charge")
    var electricityCharge = 0

    @Column(name = "security_charge")
    var securityCharge = 0

    @Column(name = "rent", nullable = false)
    var rent = 0

    @Column(name = "advance")
    var advance = 0


    fun calculateTotalUtilityCharges(): Int {
        var charge = 0
        if (waterCharge > 0) charge += waterCharge
        if (gasCharge > 0) charge += gasCharge
        if (electricityCharge > 0) charge += electricityCharge
        if (securityCharge > 0) charge += securityCharge
        return charge
    }

    fun totalCharges(): Int {
        return rent + calculateTotalUtilityCharges()
    }

    fun getWaterChargeStr(): String {
        if (waterCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*"
        return if (waterCharge == 0) "*ভাড়ার অন্তর্ভূক্ত*" else "$waterCharge টাকা"
    }

    fun getGasChargeStr(): String {
        if (gasCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*"
        return if (gasCharge == 0) "*ভাড়ার অন্তর্ভূক্ত*" else gasCharge.toString() + " টাকা"
    }

    fun getElectricityChargeStr(): String {
        if (electricityCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*"
        return if (electricityCharge == 0) "*ভাড়ার অন্তর্ভূক্ত*" else electricityCharge.toString() + " টাকা"
    }

    fun getSecurityChargeStr(): String {
        if (securityCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*"
        return if (securityCharge == 0) "*ভাড়ার অন্তর্ভূক্ত*" else securityCharge.toString() + " টাকা"
    }

}