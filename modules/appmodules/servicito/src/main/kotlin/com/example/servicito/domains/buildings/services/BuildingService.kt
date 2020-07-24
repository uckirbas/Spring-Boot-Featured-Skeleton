package com.example.servicito.domains.buildings.services

import com.example.servicito.domains.buildings.models.entities.Building
import com.example.coreweb.domains.base.services.CrudService
import com.example.auth.entities.User
import org.springframework.data.domain.Page

interface BuildingService : CrudService<Building> {
    fun findByLandlord(landlord: User): Page<Building>
}