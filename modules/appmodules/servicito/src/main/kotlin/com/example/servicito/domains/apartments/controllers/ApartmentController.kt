package com.example.servicito.domains.apartments.controllers

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudController
import com.example.servicito.commons.Constants
import com.example.servicito.domains.apartments.models.dtos.ApartmentDto
import com.example.servicito.domains.apartments.models.mappers.ApartmentMapper
import com.example.servicito.domains.apartments.services.ApartmentService
import com.example.servicito.routing.Route
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Api(tags = [Constants.Swagger.APARTMENTS])
class ApartmentController @Autowired constructor(
        private val apartmentService: ApartmentService,
        private val apartmentMapper: ApartmentMapper
) : CrudController<ApartmentDto> {

    @GetMapping(Route.V1.SEARCH_APARTMENTS)
    @ApiOperation(value = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.APARTMENTS)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("page", defaultValue = "10") size: Int): ResponseEntity<Page<ApartmentDto>> {
        val apartments = this.apartmentService.search(query, page, size)
        return ResponseEntity.ok(apartments.map { this.apartmentMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_APARTMENTS)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.APARTMENTS)
    override fun find(@PathVariable id: Long): ResponseEntity<ApartmentDto> {
        val apartment = this.apartmentService.find(id).orElseThrow { ExceptionUtil.getNotFound("apartment", id) }
        return ResponseEntity.ok(this.apartmentMapper.map(apartment))
    }

    @PostMapping(Route.V1.CREATE_APARTMENTS)
    @ApiOperation(value = Constants.Swagger.POST_MSG + Constants.Swagger.APARTMENTS)
    override fun create(@RequestBody dto: ApartmentDto): ResponseEntity<ApartmentDto> {
        val apartment = this.apartmentService.save(this.apartmentMapper.map(dto, null))
        return ResponseEntity.ok(this.apartmentMapper.map(apartment))
    }

    @PatchMapping(Route.V1.UPDATE_APARTMENTS)
    @ApiOperation(value = Constants.Swagger.PATCH_MSG + Constants.Swagger.APARTMENTS)
    override fun update(@PathVariable id: Long,
                        @RequestBody dto: ApartmentDto): ResponseEntity<ApartmentDto> {
        var apartment = this.apartmentService.find(id).orElseThrow { ExceptionUtil.getNotFound("apartment", id) }
        apartment = this.apartmentService.save(this.apartmentMapper.map(dto, apartment))
        return ResponseEntity.ok(this.apartmentMapper.map(apartment))
    }

    @DeleteMapping(Route.V1.DELETE_APARTMENTS)
    @ApiOperation(value = Constants.Swagger.DELETE_MSG + Constants.Swagger.APARTMENTS)
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.apartmentService.delete(id, true)
        return ResponseEntity.noContent().build()
    }

    @GetMapping(Route.V1.GET_APARTMENTS_FOR_BUILDING)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.APARTMENTS_FOR_BUILDING)
    fun apartmentsForBuilding(@PathVariable("building_id") buildingId: Long): ResponseEntity<Any> {
        val apartments = this.apartmentService.getForBuilding(buildingId)
        return ResponseEntity.ok(apartments.map { this.apartmentMapper.map(it) })
    }
}