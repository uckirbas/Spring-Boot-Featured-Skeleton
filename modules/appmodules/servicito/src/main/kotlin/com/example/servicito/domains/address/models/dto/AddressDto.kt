package com.example.servicito.domains.address.models.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.example.coreweb.domains.base.models.dtos.BaseDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Column
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@ApiModel(description = "Model to create a new Address")
class AddressDto : BaseDto() {

    @JsonProperty( "flat")
    var flat: String? = null

    @JsonProperty( "floor")
    @NotNull
    var floor: Byte = 0

    @JsonProperty( "house")
    var house: String? = null

    @JsonProperty( "road")
    var road: String? = null

    @JsonProperty( "post_code")
    var postCode: String? = null

    @NotBlank
    @JsonProperty( "area")
    lateinit var area: String
    
    @NotNull
    @JsonProperty("division_id")
    @ApiModelProperty(notes = "provide division id for division record", example = "1", required = true)
    var divisionId: Long? = null

    @NotNull
    @JsonProperty("district_id")
    @ApiModelProperty(notes = "provide district id for district record", example = "1", required = true)
    var districtId: Long? = null

    @NotNull
    @JsonProperty("upazila_id")
    @ApiModelProperty(notes = "provide upazila id for upazila record", example = "1", required = true)
    var upazilaId: Long? = null

    @NotNull
    @JsonProperty("union_id")
    @ApiModelProperty(notes = "provide union id for union record", example = "1", required = true)
    var unionId: Long? = null

    @NotNull
    @JsonProperty("village_id")
    @ApiModelProperty(notes = "provide village id for village record", example = "1", required = true)
    var villageId: Long? = null

    @JsonProperty("lat")
    @ApiModelProperty(notes = "provide latitude number for location tracking", example = "23.777176")
    var lat: Double? = null

    @JsonProperty("lng")
    @ApiModelProperty(notes = "provide longitude number for location tracking", example = "90.399452")
    var lng: Double? = null
}
