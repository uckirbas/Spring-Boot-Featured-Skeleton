package com.example.servicito.domains.buildings.controllers.api

import com.example.common.utils.ExceptionUtil
import com.example.servicito.domains.buildings.models.dtos.BuildingDto
import com.example.servicito.domains.buildings.models.mappers.BuildingMapper
import com.example.servicito.domains.buildings.services.BuildingService
import com.example.coreweb.domains.base.controllers.CrudController
import com.example.servicito.commons.Constants
import com.example.servicito.routing.Route
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Api(tags = [Constants.Swagger.BUILDING], description = Constants.Swagger.REST_API)
class BuildingsController @Autowired constructor(
        private val buildingService: BuildingService,
        private val buildingMapper: BuildingMapper
) : CrudController<BuildingDto> {

    @GetMapping(Route.V1.SEARCH_BUILDINGS)
    @ApiOperation(value = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.BUILDING)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("page", defaultValue = "10") size: Int): ResponseEntity<Page<BuildingDto>> {
        val buildings = this.buildingService.search(query, page,size)
        return ResponseEntity.ok(buildings.map { this.buildingMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_BUILDINGS)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.BUILDING)
    override fun find(@PathVariable id: Long): ResponseEntity<BuildingDto> {
        val building = this.buildingService.find(id).orElseThrow { ExceptionUtil.getNotFound("Building", id) }
        return ResponseEntity.ok(this.buildingMapper.map(building))
    }

    @PostMapping(Route.V1.CREATE_BUILDINGS)
    @ApiOperation(value = Constants.Swagger.POST_MSG + Constants.Swagger.BUILDING)
    override fun create(@RequestBody dto: BuildingDto): ResponseEntity<BuildingDto> {
        val building = this.buildingService.save(this.buildingMapper.map(dto, null))
        return ResponseEntity.ok(this.buildingMapper.map(building))
    }

    @PatchMapping(Route.V1.UPDATE_BUILDINGS)
    @ApiOperation(value = Constants.Swagger.PATCH_MSG + Constants.Swagger.BUILDING)
    override fun update(@PathVariable id: Long, @RequestBody dto: BuildingDto): ResponseEntity<BuildingDto> {
        var building = this.buildingService.find(id).orElseThrow { ExceptionUtil.getNotFound("Building", id) }
        building = this.buildingService.save(this.buildingMapper.map(dto, building))
        return ResponseEntity.ok(this.buildingMapper.map(building))
    }

    @DeleteMapping(Route.V1.DELETE_BUILDINGS)
    @ApiOperation(value = Constants.Swagger.DELETE_MSG + Constants.Swagger.BUILDING)
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.buildingService.delete(id, true)
        return ResponseEntity.ok().build()
    }
}