package com.example.coreweb.domains.base.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

open class BaseDto : Serializable {
    @JsonProperty("id")
    @Schema(readOnly = true, example = "1")
    var id: Long? = null

    @JsonProperty(value = "created_at")
    @Schema(readOnly = true, example = "1533115869000", description = "Date when this entity was first created.")
    var createdAt: Long = 0

    @JsonProperty("updated_at")
    @Schema(readOnly = true, example = "1596274269000", description = "Date when this entity was last updated")
    var updatedAt: Long = 0
}
