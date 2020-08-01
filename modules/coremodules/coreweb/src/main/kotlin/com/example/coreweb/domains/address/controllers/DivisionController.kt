package com.example.coreweb.domains.address.controllers

import com.example.coreweb.commons.Constants
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.address.models.dto.DivisionDto
import com.example.coreweb.domains.address.models.entities.Division
import com.example.coreweb.domains.address.models.mappers.DivisionMapper
import com.example.coreweb.domains.address.services.DivisionService
import com.example.coreweb.routing.Route
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Tag(name = Constants.Swagger.DIVISION, description = Constants.Swagger.REST_API)
class DivisionController(
        @Autowired val divisionService: DivisionService,
        @Autowired val divisionMapper: DivisionMapper
) {

    @GetMapping(Route.V1.SEARCH_DIVISION)
    @Operation(summary = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.DIVISION)
    fun search(@RequestParam("q", defaultValue = "") query: String,
               @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<DivisionDto>> {
        val divisions: Page<Division> = this.divisionService.search(query, page, size)
        return ResponseEntity.ok(divisions.map { division -> this.divisionMapper.map(division) })
    }

    @GetMapping(Route.V1.FIND_DIVISION)
    @Operation(summary = Constants.Swagger.GET_MSG + Constants.Swagger.DIVISION)
    fun find(@PathVariable id: Long): ResponseEntity<DivisionDto> {
        val division: Division = this.divisionService.find(id).orElseThrow { ExceptionUtil.notFound("Could not find division with id: $id") }
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @PostMapping(Route.V1.CREATE_DIVISION)
    @Operation(summary = Constants.Swagger.POST_MSG + Constants.Swagger.DIVISION)
    fun save(@Valid @RequestBody divisionDto: DivisionDto): ResponseEntity<DivisionDto> {
        val division: Division = this.divisionService.save(this.divisionMapper.map(divisionDto, null))
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @PatchMapping(Route.V1.UPDATE_DIVISION)
    @Operation(summary = Constants.Swagger.PATCH_MSG + Constants.Swagger.DIVISION)
    fun update(@Valid @RequestBody divisionDto: DivisionDto, @PathVariable id: Long): ResponseEntity<DivisionDto> {
        var division: Division = this.divisionService.find(id).orElseThrow { ExceptionUtil.notFound("Could not find division with id: $id") }
        division = this.divisionService.save(this.divisionMapper.map(divisionDto, division))
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @DeleteMapping(Route.V1.DELETE_DIVISION)
    @Operation(summary = Constants.Swagger.DELETE_MSG + Constants.Swagger.DIVISION)
    fun delete(@PathVariable id: Long): ResponseEntity<DivisionDto> {
        this.divisionService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
