package com.example.servicito.domains.buildings.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.example.coreweb.domains.base.models.dtos.BaseDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.validation.constraints.NotNull

@ApiModel(description = "Model to Create/update new Building")
class BuildingDto : BaseDto() {
    @NotNull
    @JsonProperty("name")
    @ApiModelProperty(example = "Chayyanir House", required = true)
    lateinit var name: String

    @NotNull
    @JsonProperty("address_id")
    @ApiModelProperty(example = "1", required = true)
    var addressId: Long? = null

    var landmarks: String? = null

    @JsonProperty("distance_from_busstand")
    var distanceFromBusstand: String? = null

    @JsonProperty("nearby_hospitals")
    var nearbyHospitals: String? = null

    @JsonProperty("nearby_police_station")
    var nearbyPoliceStation: String? = null

    @JsonProperty("nearby_schools")
    var nearbySchools: String? = null

    @JsonProperty("available_transports")
    var availableTransportations: String? = null

    @JsonProperty("area_type")
    var areaType: String? = null

    @JsonProperty("security_types")
    var securityType: String? = null

    @JsonProperty("has_lift")
    var hasLift: Boolean = false

    @JsonProperty("marketplaces")
    var marketPlaces: String? = null

    @JsonProperty("total_floors")
    var totalNumberOfFloor: Byte = 0

    @JsonProperty("total_flats")
    var totalNumberOfFlat: Int = 0

    var verified: Boolean = false

    @JsonProperty("video_links")
    var videoLink: String? = null

    @ElementCollection
    @CollectionTable(name = "building_image_paths")
    @JsonProperty("image_paths")
    var imagePaths: MutableList<String>? = null

}