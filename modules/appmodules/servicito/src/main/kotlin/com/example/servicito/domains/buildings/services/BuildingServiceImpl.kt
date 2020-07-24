package com.example.servicito.domains.buildings.services

import com.example.auth.entities.User
import com.example.common.exceptions.notfound.NotFoundException
import com.example.coreweb.utils.PageAttr
import com.example.servicito.domains.buildings.models.entities.Building
import com.example.servicito.domains.buildings.repositories.BuildingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class BuildingServiceImpl @Autowired constructor(
        private val buildingRepo: BuildingRepository
) : BuildingService {

    override fun findByLandlord(landlord: User): Page<Building> {
        return this.findByLandlord(landlord)
    }

    override fun search(query: String, page: Int, size: Int): Page<Building> {
        return this.buildingRepo.search(query, PageAttr.getPageRequest(page, size))
    }

    override fun save(entity: Building): Building {
        return this.buildingRepo.save(entity)
    }

    override fun find(id: Long): Optional<Building> {
        return this.buildingRepo.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val building: Building = this.find(id).orElseThrow { NotFoundException("Couldn't find building with id: $id") }
            building.isDeleted = true
            this.save(building)
        } else
            this.buildingRepo.deleteById(id)
    }

}