package com.example.servicito.domains.apartments.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.example.servicito.domains.apartments.models.embedables.Charge
import com.example.coreweb.domains.base.models.dtos.BaseDto
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.NotNull

@ApiModel(description = "Model to create/update Apartments inside building")
class ApartmentDto : BaseDto() {
    @NotNull
    @JsonProperty("building_id")
    @ApiModelProperty(example = "1", required = true)
    var buildingId: Long? = null

    @NotNull
    @JsonProperty("apartment_number")
    @ApiModelProperty(example = "205", required = true)
    lateinit var apartmentNumber: String

    @JsonProperty("number_of_bed")
    var numberOfBed: Byte = 0

    @JsonProperty("number_of_bath")
    var numberOfBath: Byte = 0

    @JsonProperty("has_kitchen")
    var hasKitchen = false

    @JsonProperty("has_dining")
    var hasDining = false

    @JsonProperty("has_drawing")
    var hasDrawing = false

    @JsonProperty("number_of_balcony")
    var numberOfBalcony: Byte = 0

    @JsonProperty("floor_number")
    var floorNumber: Byte = 0

    @JsonProperty("has_gas")
    var hasGas = false

    @JsonProperty("has_water")
    var hasWater = false

    @JsonProperty("has_electricity")
    var hasElectricity = false

    @JsonProperty("has_roof_access")
    var hasRoofAccess = false

    @JsonProperty("has_geyser")
    var hasGeyser = false

    var tiled = false

    var furnished = false

    @JsonProperty("window_type")
    var windowType: String? = null

    var faced: String? = null

    var description: String? = null

    @JsonProperty("civil_design")
    var civilDesign: String? = null

    var verified = false

    @JsonProperty("available_from")
    var availableFrom: Date? = null

    @JsonProperty("apartment_size")
    var apartmentSize = 0

    var available = false

    @JsonProperty("video_link")
    var videoLink: String? = null

    @NotNull
    @JsonProperty("bachelor_allowed")
    var bachelorAllowed: Boolean = false

    @JsonProperty("total_visit_count")
    var totalVisitCount: Long = 0

    @JsonProperty("visit_count_after_marked_available")
    var visitCountAfterMarkedAvailable: Long = 0

    @JsonProperty("security_token")
    lateinit var securityToken: String

    @NotNull
    @JsonProperty("images")
    @ApiModelProperty(dataType = "List", required = true)
    lateinit var images: MutableList<String>

    @NotNull
    @ApiModelProperty(required = true)
    lateinit var charges: ChargeDto

    var fixed = false

    @JsonProperty("first_image_url", access = JsonProperty.Access.READ_ONLY)
    lateinit var firstImageUrl: String

    @JsonProperty("available_date_readable", access = JsonProperty.Access.READ_ONLY)
    lateinit var availableDateReadable: String

    @JsonProperty("link", access = JsonProperty.Access.READ_ONLY)
    lateinit var link: String
}