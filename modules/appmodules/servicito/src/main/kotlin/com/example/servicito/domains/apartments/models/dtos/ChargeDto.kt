package com.example.servicito.domains.apartments.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.example.coreweb.domains.base.models.dtos.BaseDto
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ChargeDto {

    @JsonProperty("gas_charge")
    var gasCharge = 0

    @JsonProperty("water_charge")
    var waterCharge = 0

    @JsonProperty("electricity_charge")
    var electricityCharge = 0

    @JsonProperty("security_charge")
    var securityCharge = 0

    @JsonProperty("rent")
    var rent = 0

    @JsonProperty("advance")
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

    @JsonProperty("water_charge_str")
    fun waterChargeStr(): String {
        if (waterCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*"
        return if (waterCharge == 0) "*ভাড়ার অন্তর্ভূক্ত*" else "$waterCharge টাকা"
    }

    @JsonProperty("gas_charge_str")
    fun gasChargeStr(): String {
        if (gasCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*"
        return if (gasCharge == 0) "*ভাড়ার অন্তর্ভূক্ত*" else gasCharge.toString() + " টাকা"
    }

    @JsonProperty("electricity_charge_str")
    fun electricityChargeStr(): String {
        if (electricityCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*"
        return if (electricityCharge == 0) "*ভাড়ার অন্তর্ভূক্ত*" else electricityCharge.toString() + " টাকা"
    }

    @JsonProperty("security_charge_str")
    fun securityChargeStr(): String {
        if (securityCharge < 0) return "*ব্যাবহারের উপর নির্ভরশীল*"
        return if (securityCharge == 0) "*ভাড়ার অন্তর্ভূক্ত*" else securityCharge.toString() + " টাকা"
    }

}