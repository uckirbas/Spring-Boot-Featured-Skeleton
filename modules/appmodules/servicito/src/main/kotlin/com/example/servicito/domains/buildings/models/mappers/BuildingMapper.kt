package com.example.servicito.domains.buildings.models.mappers

import com.example.common.utils.ExceptionUtil
import com.example.auth.config.security.SecurityContext
import com.example.servicito.domains.buildings.models.dtos.BuildingDto
import com.example.servicito.domains.buildings.models.entities.Building
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.auth.entities.User
import com.example.servicito.domains.address.services.AddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BuildingMapper @Autowired constructor(
        private val addressService: AddressService
) : BaseMapper<Building, BuildingDto> {


    override fun map(entity: Building): BuildingDto {
        val dto = BuildingDto()
        dto.id = entity.id
        dto.created = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.name = entity.name
        dto.addressId = entity.address.id
        dto.landmarks = entity.landmarks
        dto.distanceFromBusstand = entity.distanceFromBusstand
        dto.nearbyHospitals = entity.nearbyHospitals
        dto.nearbyPoliceStation = entity.nearbyPoliceStation
        dto.nearbySchools = entity.nearbySchools
        dto.availableTransportations = entity.availableTransportations
        dto.areaType = entity.areaType
        dto.securityType = entity.securityType
        dto.hasLift = entity.hasLift
        dto.marketPlaces = entity.marketPlaces
        dto.totalNumberOfFloor = entity.totalNumberOfFloor
        dto.totalNumberOfFlat = entity.totalNumberOfFlat
        dto.verified = entity.verified
        dto.videoLink = entity.videoLink
        dto.imagePaths = entity.imagePaths

        return dto
    }

    override fun map(dto: BuildingDto, exEntity: Building?): Building {
        val entity = exEntity ?: Building()

        entity.name = dto.name
        entity.address = dto.addressId?.let { this.addressService.find(it).orElseThrow { ExceptionUtil.getNotFound("Address", dto.addressId) } }!!
        entity.landmarks = dto.landmarks
        entity.distanceFromBusstand = dto.distanceFromBusstand
        entity.nearbyHospitals = dto.nearbyHospitals
        entity.nearbyPoliceStation = dto.nearbyPoliceStation
        entity.nearbySchools = dto.nearbySchools
        entity.availableTransportations = dto.availableTransportations
        entity.areaType = dto.areaType
        entity.securityType = dto.securityType
        entity.hasLift = dto.hasLift
        entity.marketPlaces = dto.marketPlaces
        entity.totalNumberOfFloor = dto.totalNumberOfFloor
        entity.totalNumberOfFlat = dto.totalNumberOfFlat
        entity.verified = dto.verified
        entity.videoLink = dto.videoLink
        entity.imagePaths = dto.imagePaths
        entity.landlord = User(SecurityContext.getCurrentUser())
        entity.infoCollectedBy = User(SecurityContext.getCurrentUser())

        return entity
    }


}