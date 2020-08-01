package com.example.acl.domains.users.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

@Schema(description = "To create a privilege with permission to access certain urls")
class PrivilegeDto : BaseDto() {
    @NotBlank
    @Schema(name = "A Unique string without space", example = "ACCESS_USER_DATA")
    lateinit var name: String

    var description: String? = null

    @NotBlank
    @Schema(name = "A readable label for the Privilege", example = "Read User Data")
    lateinit var label: String

    @NotBlank.List
    @JsonProperty("access_urls")
    @Schema(name = "access_urls")
    lateinit var accessUrls: List<String>

}
