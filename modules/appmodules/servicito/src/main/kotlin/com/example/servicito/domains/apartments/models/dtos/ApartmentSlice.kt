package com.example.servicito.domains.apartments.models.dtos

class ApartmentSlice {
    var id: Long? = null
    var apartmentNumber: String? = null
    var totalVisitCount: Long = 0
    var visitCountAfterMarkedAvailable: Long = 0

    constructor() {}
    constructor(id: Long?, apartmentNumber: String?, totalVisitCount: Long, visitCountAfterMarkedAvailable: Long) {
        this.id = id
        this.apartmentNumber = apartmentNumber
        this.totalVisitCount = totalVisitCount
        this.visitCountAfterMarkedAvailable = visitCountAfterMarkedAvailable
    }

}
