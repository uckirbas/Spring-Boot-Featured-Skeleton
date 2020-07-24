package com.example.servicito.domains.apartments.models.mappers

import com.example.common.utils.ExceptionUtil
import com.example.auth.config.security.SecurityContext
import com.example.servicito.domains.apartments.models.dtos.ApartmentDto
import com.example.servicito.domains.apartments.models.entities.Apartment
import com.example.servicito.domains.buildings.services.BuildingService
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.auth.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ApartmentMapper @Autowired constructor(
        private val buildingService: BuildingService,
        private val chargesMapper: ChargesMapper
) : BaseMapper<Apartment, ApartmentDto> {

    @Value("\${baseUrlApi}")
    lateinit var baseApiUrl: String

    override fun map(entity: Apartment): ApartmentDto {
        val dto = ApartmentDto()
        dto.id = entity.id
        dto.created = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.buildingId = entity.building.id
        dto.apartmentNumber = entity.apartmentNumber
        dto.numberOfBed = entity.numberOfBed
        dto.numberOfBath = entity.numberOfBath
        dto.hasKitchen = entity.hasKitchen
        dto.hasDining = entity.hasKitchen
        dto.hasDrawing = entity.hasKitchen
        dto.numberOfBalcony = entity.numberOfBalcony
        dto.floorNumber = entity.floorNumber
        dto.hasGas = entity.hasGas
        dto.hasWater = entity.hasWater
        dto.hasElectricity = entity.hasElectricity
        dto.hasRoofAccess = entity.hasRoofAccess
        dto.hasGeyser = entity.hasGeyser
        dto.tiled = entity.tiled
        dto.furnished = entity.furnished
        dto.windowType = entity.windowType
        dto.faced = entity.faced
        dto.description = entity.description
        dto.verified = entity.hasKitchen
        dto.availableFrom = entity.availableFrom
        dto.available = entity.available
        dto.videoLink = entity.videoLink
        dto.bachelorAllowed = entity.bachelorAllowed
        dto.totalVisitCount = entity.totalVisitCount
        dto.visitCountAfterMarkedAvailable = entity.visitCountAfterMarkedAvailable
        dto.securityToken = entity.securityToken
        dto.images = entity.images
        dto.charges = this.chargesMapper.map(entity.charges)
        dto.fixed = entity.fixed

        dto.firstImageUrl = entity.firstImageUrl()
        dto.availableDateReadable = entity.getAvailableDateReadable()
        dto.link = "$baseApiUrl${entity.getLink()}"

        return dto
    }

    override fun map(dto: ApartmentDto, exEntity: Apartment?): Apartment {
        val entity = exEntity ?: Apartment()

        entity.building = dto.buildingId?.let { this.buildingService.find(it).orElseThrow { ExceptionUtil.getNotFound("Building", dto.buildingId) } }!!
        entity.apartmentNumber = dto.apartmentNumber
        entity.numberOfBed = dto.numberOfBed
        entity.numberOfBath = dto.numberOfBath
        entity.hasKitchen = dto.hasKitchen
        entity.hasDining = dto.hasKitchen
        entity.hasDrawing = dto.hasKitchen
        entity.numberOfBalcony = dto.numberOfBalcony
        entity.floorNumber = dto.floorNumber
        entity.hasGas = dto.hasGas
        entity.hasWater = dto.hasWater
        entity.hasElectricity = dto.hasElectricity
        entity.hasRoofAccess = dto.hasRoofAccess
        entity.hasGeyser = dto.hasGeyser
        entity.tiled = dto.tiled
        entity.furnished = dto.furnished
        entity.windowType = dto.windowType
        entity.faced = dto.faced
        entity.description = dto.description
        entity.verified = dto.hasKitchen
        entity.availableFrom = dto.availableFrom
        entity.available = dto.available
        entity.videoLink = dto.videoLink
        entity.bachelorAllowed = dto.bachelorAllowed
        entity.totalVisitCount = dto.totalVisitCount
        entity.visitCountAfterMarkedAvailable = dto.visitCountAfterMarkedAvailable
        entity.securityToken = dto.securityToken
        entity.images = dto.images
        entity.charges = this.chargesMapper.map(dto.charges)
        entity.fixed = dto.fixed

        entity.infoCollectedBy = User(SecurityContext.getCurrentUser())

        return entity
    }

}