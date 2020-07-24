package com.example.servicito.domains.apartments.services

import com.example.servicito.domains.address.models.dto.LatLngDto
import com.example.servicito.domains.apartments.models.entities.Apartment
import com.example.coreweb.domains.base.services.CrudService
import com.example.servicito.domains.search.models.responses.LatLngSearchResponse
import java.util.*

interface ApartmentService : CrudService<Apartment> {
    fun getLatLongByAreaV2(area: String, bachelorAllowed: Boolean): LatLngSearchResponse

    fun filterLatLongByAreaV2(area: String, bachelorAllowed: Boolean, noOfBed: Int, rentFrom: Int, rentTo: Int): LatLngSearchResponse

    fun getNearbyLatLongs(latLng: LatLngDto, bachelorAllowed: Boolean): List<LatLngDto>

    fun findWithinPeriod(fromDate: Date, toDate: Date): List<Apartment>

    fun getForBuilding(buildingId: Long): List<Apartment>
}