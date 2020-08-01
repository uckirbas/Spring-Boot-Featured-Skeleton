package com.example.coreweb.domains.address.controllers

import com.example.coreweb.commons.Constants
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.address.models.dto.DistrictDto
import com.example.coreweb.domains.address.models.entities.District
import com.example.coreweb.domains.address.models.mappers.DistrictMapper
import com.example.coreweb.domains.address.services.DistrictService
import com.example.coreweb.domains.base.controllers.CrudController
import com.example.coreweb.routing.Route
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Tag(name = Constants.Swagger.DISTRICT, description = Constants.Swagger.REST_API)
class DistrictController(
        @Autowired val districtService: DistrictService,
        @Autowired val districtMapper: DistrictMapper
) : CrudController<DistrictDto> {

    @GetMapping(Route.V1.SEARCH_DISTRICT)
    @Operation(summary = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.DISTRICT)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<DistrictDto>> {
        val districts: Page<District> = this.districtService.search(query, page, size)
        return ResponseEntity.ok(districts.map { district -> this.districtMapper.map(district) })
    }

    @GetMapping(Route.V1.FIND_DISTRICT)
    @Operation(summary = Constants.Swagger.GET_MSG + Constants.Swagger.DISTRICT)
    override fun find(@PathVariable id: Long): ResponseEntity<DistrictDto> {
        val district: District = this.districtService.find(id).orElseThrow { ExceptionUtil.notFound("Could not find district with id: $id") }
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @PostMapping(Route.V1.CREATE_DISTRICT)
    @Operation(summary = Constants.Swagger.POST_MSG + Constants.Swagger.DISTRICT)
    override fun create(@Valid @RequestBody dto: DistrictDto): ResponseEntity<DistrictDto> {
        val district: District = this.districtService.save(this.districtMapper.map(dto, null))
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @PatchMapping(Route.V1.UPDATE_DISTRICT)
    @Operation(summary = Constants.Swagger.PATCH_MSG + Constants.Swagger.DISTRICT)
    override fun update(@PathVariable id: Long, @Valid @RequestBody dto: DistrictDto): ResponseEntity<DistrictDto> {
        var district: District = this.districtService.find(id).orElseThrow { ExceptionUtil.notFound("Could not find district with id: $id") }
        district = this.districtService.save(this.districtMapper.map(dto, district))
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @DeleteMapping(Route.V1.DELETE_DISTRICT)
    @Operation(summary = Constants.Swagger.DELETE_MSG + Constants.Swagger.DISTRICT)
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.districtService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
