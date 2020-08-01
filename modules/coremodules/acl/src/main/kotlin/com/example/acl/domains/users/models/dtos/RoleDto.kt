package com.example.acl.domains.users.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class RoleDto : BaseDto() {
    @NotBlank
    lateinit var name: String

    var description: String? = null

    @NotNull
    var restricted: Boolean = false

    @Schema(readOnly = true)
    var privileges: List<PrivilegeDto>? = null

    @NotNull
    @NotEmpty
    @JsonProperty(value = "privilege_ids")
    @Schema(name = "privilege_ids")
    lateinit var privilegeIds: List<Long>


}
