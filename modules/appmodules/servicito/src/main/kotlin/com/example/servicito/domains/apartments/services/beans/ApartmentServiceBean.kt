package com.example.servicito.domains.apartments.services.beans

import com.example.coreweb.utils.PageAttr
import com.example.common.utils.ExceptionUtil
import com.example.servicito.domains.address.models.dto.LatLngDto
import com.example.servicito.domains.address.models.entities.LatLng
import com.example.servicito.domains.address.models.entities.LatLng.Companion.parse
import com.example.servicito.domains.apartments.models.entities.Apartment
import com.example.servicito.domains.apartments.repositories.ApartmentRepository
import com.example.servicito.domains.apartments.services.ApartmentService
import com.example.servicito.domains.buildings.models.pojo.LatLngMeta
import com.example.servicito.domains.search.models.responses.LatLngSearchResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*


@Service
class ApartmentServiceBean @Autowired constructor(
        private val apartmentRepository: ApartmentRepository
) : ApartmentService {

    override fun search(query: String, page: Int, size: Int): Page<Apartment> {
        return this.apartmentRepository.search(query, PageAttr.getPageRequest(page,size))
    }

    override fun save(entity: Apartment): Apartment {
        return this.apartmentRepository.save(entity)
    }

    override fun find(id: Long): Optional<Apartment> {
        return this.apartmentRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (!softDelete) {
            this.apartmentRepository.deleteById(id)
            return
        }
        val apartment = this.apartmentRepository.find(id).orElseThrow { ExceptionUtil.getNotFound("apartment", id) }
        apartment.isDeleted = true
        this.apartmentRepository.save(apartment)
    }

    override fun getLatLongByAreaV2(area: String, bachelorAllowed: Boolean): LatLngSearchResponse {
        val llMap = parse(area)
        val latLng = LatLngDto(llMap["lat"] ?: 0.toDouble(), llMap["lng"] ?: 0.toDouble())
        return LatLngSearchResponse(area, latLng, getNearbyLatLongs(latLng, bachelorAllowed))
    }

    override fun filterLatLongByAreaV2(area: String, bachelorAllowed: Boolean, noOfBed: Int, rentFrom: Int, rentTo: Int): LatLngSearchResponse {
        val llMap = parse(area)
        val latLng = LatLngDto(llMap["lat"] ?: 0.toDouble(), llMap["lng"] ?: 0.toDouble())
        val objects = apartmentRepository.filterNearbyLatLngBachelor(latLng.lat, latLng.lng, bachelorAllowed, rentFrom, rentTo, noOfBed)
        return LatLngSearchResponse(area, latLng, parseLatlngs(objects))
    }


    override fun getNearbyLatLongs(latLng: LatLngDto, bachelorAllowed: Boolean): List<LatLngDto> {
        val objects: List<Array<Any>>
        if (bachelorAllowed) objects = apartmentRepository.findNearbyLatLngBachelor(latLng.lat, latLng.lng, true) else objects = apartmentRepository.findNearbyLatLng(latLng.lat, latLng.lng)
        return this.parseLatlngs(objects)
    }

    override fun findWithinPeriod(fromDate: Date, toDate: Date): List<Apartment> {
        return this.apartmentRepository.findWithinPeriod(fromDate, toDate)
    }

    override fun getForBuilding(buildingId: Long): List<Apartment> {
        return this.apartmentRepository.findByBuilding(buildingId)
    }

    private fun parseLatlngs(objects: List<Array<Any>>): List<LatLngDto> {
        val latLngs: MutableList<LatLngDto> = ArrayList()
        for (obj in objects) {
            val latLng = LatLngDto()
            latLng.lat = (obj[0] as Double)
            latLng.lng = (obj[1] as Double)
            latLng.meta = (LatLngMeta((obj[2] as Byte), (obj[3] as Int), (obj[4] as Boolean), (obj[5] as Boolean), (obj[6] as Boolean), obj[7] as Long, (obj[8] as String)))
            latLngs.add(latLng)
        }
        return latLngs
    }

}