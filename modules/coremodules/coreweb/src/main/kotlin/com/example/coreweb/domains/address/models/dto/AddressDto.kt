package com.example.coreweb.domains.address.models.dto

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

@Schema(description = "Model to create a new Address")
class AddressDto : BaseDto() {

    @NotNull
    @JsonProperty("division_id")
    @Schema(description = "provide division id for division record", example = "1", required = true)
    var divisionId: Long? = null

    @NotNull
    @JsonProperty("district_id")
    @Schema(description = "provide district id for district record", example = "1", required = true)
    var districtId: Long? = null

    @NotNull
    @JsonProperty("upazila_id")
    @Schema(description = "provide upazila id for upazila record", example = "1", required = true)
    var upazilaId: Long? = null

    @NotNull
    @JsonProperty("union_id")
    @Schema(description = "provide union id for union record", example = "1", required = true)
    var unionId: Long? = null

    @NotNull
    @JsonProperty("village_id")
    @Schema(description = "provide village id for village record", example = "1", required = true)
    var villageId: Long? = null

    @JsonProperty("lat")
    @Schema(description = "provide latitude number for location tracking", example = "23.777176")
    var lat: Double? = null

    @JsonProperty("lng")
    @Schema(description = "provide longitude number for location tracking", example = "90.399452")
    var lng: Double? = null
}
