package com.example.servicito.domains.address.services

import com.example.servicito.domains.address.models.entities.Address
import com.example.servicito.domains.buildings.models.entities.Building
import com.example.coreweb.domains.base.services.CrudService

interface AddressService : CrudService<Address> {
    // FIXME: Remove after migration
    fun convertAndSaveFromBuildings(buildings: List<Building>)
}
