package com.example.servicito.domains.address.models.mappers

import com.example.servicito.domains.address.models.dto.AddressDto
import com.example.servicito.domains.address.models.entities.LatLng
import com.example.servicito.domains.address.models.entities.Address
import com.example.servicito.domains.address.services.*
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AddressMapper @Autowired constructor(
        private val divisionService: DivisionService,
        private val districtService: DistrictService,
        private val upazilaService: UpazilaService,
        private val unionService: UnionService,
        private val villageService: VillageService
) : BaseMapper<Address, AddressDto> {

    override fun map(entity: Address): AddressDto {
        val dto = AddressDto()
        dto.id = entity.id
        dto.created = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.flat = entity.flat
        dto.floor = entity.floor
        dto.house = entity.house
        dto.road = entity.road
        dto.postCode = entity.postCode
        dto.area = entity.area
        dto.divisionId = entity.division?.id
        dto.districtId = entity.district?.id
        dto.upazilaId = entity.upazila?.id
        dto.unionId = entity.union?.id
        dto.villageId = entity.village?.id
        dto.lat = entity.latLng?.lat
        dto.lng = entity.latLng?.lng
        return dto
    }

    override fun map(dto: AddressDto, exEntity: Address?): Address {
        val entity = exEntity ?: Address()
        entity.division = dto.divisionId?.let { this.divisionService.find(it).orElseThrow { NotFoundException("Could not find division with id: " + entity.division?.id) } }
        entity.district = dto.districtId?.let { this.districtService.find(it).orElseThrow { NotFoundException("Could not find district with id: " + entity.district?.id) } }
        entity.upazila = dto.upazilaId?.let { this.upazilaService.find(it).orElseThrow { NotFoundException("Could not find upazila with id: " + entity.upazila?.id) } }
        entity.union = dto.unionId?.let { this.unionService.find(it).orElseThrow { NotFoundException("Could not find union with id: " + entity.union?.id) } }
        entity.village = dto.villageId?.let { this.villageService.find(it).orElseThrow { NotFoundException("Could not find village with id: " + entity.village?.id) } }
        if (entity.latLng == null) entity.latLng = LatLng()
        entity.flat = dto.flat
        entity.floor = dto.floor
        entity.house = dto.house
        entity.road = dto.road
        entity.postCode = dto.postCode
        entity.area = dto.area
        entity.latLng!!.lat = dto.lat ?: entity.latLng!!.lat
        entity.latLng!!.lng = dto.lng ?: entity.latLng!!.lng
        return entity
    }

}
