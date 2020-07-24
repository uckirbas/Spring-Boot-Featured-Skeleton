package com.example.servicito.domains.buildings.models.pojo

import com.example.common.utils.DateUtil
import java.util.*

class LatLngMeta {
    var id: Long? = null
    var token: String? = null
    var noOfBeds: Byte? = null
    var rent = 0
    var available = false
    var bachelorAllowed = false
    var verified = false
    // for plasma blood database
    var bloodGroup: String? = null
    var lastDonated: Date? = null
    var userId: Long? = null

    constructor()

    constructor(noOfBeds: Byte, rent: Int) {
        this.noOfBeds = noOfBeds
        this.rent = rent
    }

    constructor(noOfBeds: Byte, rent: Int, available: Boolean, bachelorAllowed: Boolean, verified: Boolean, id: Long, token: String) {
        this.noOfBeds = noOfBeds
        this.rent = rent
        this.available = available
        this.bachelorAllowed = bachelorAllowed
        this.verified = verified
        this.id = id
        this.token = token
    }

    constructor(bloodGroup: String, lastDonated: Date, userId: Long) {
        this.bloodGroup = bloodGroup
        this.lastDonated = lastDonated
        this.userId = userId
    }

    val lastDonatedStr: String
        get() = if (lastDonated == null) "" else DateUtil.getServerDateTimeFormat().format(lastDonated)
}