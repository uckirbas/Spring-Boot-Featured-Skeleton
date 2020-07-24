package com.example.servicito.domains.apartments.models.mappers

import com.example.servicito.domains.apartments.models.dtos.ChargeDto
import com.example.servicito.domains.apartments.models.embedables.Charge
import org.springframework.stereotype.Component

@Component
class ChargesMapper {

    fun map(charge: Charge): ChargeDto {
        val dto = ChargeDto()
        dto.gasCharge = charge.gasCharge
        dto.waterCharge = charge.waterCharge
        dto.electricityCharge = charge.electricityCharge
        dto.securityCharge = charge.securityCharge
        dto.rent = charge.rent
        dto.advance = charge.advance
        return dto
    }

    fun map(dto: ChargeDto): Charge {
        val charge = Charge()
        charge.gasCharge = dto.gasCharge
        charge.waterCharge = dto.waterCharge
        charge.electricityCharge = dto.electricityCharge
        charge.securityCharge = dto.securityCharge
        charge.rent = dto.rent
        charge.advance = dto.advance
        return charge
    }

}