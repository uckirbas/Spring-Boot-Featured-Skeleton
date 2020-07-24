package com.example.servicito.domains.address.services.beans

import com.example.common.misc.Commons
import com.example.coreweb.utils.PageAttr
import com.example.servicito.domains.address.models.entities.Address
import com.example.servicito.domains.address.models.entities.Union
import com.example.servicito.domains.address.models.entities.Upazila
import com.example.servicito.domains.address.models.entities.Village
import com.example.servicito.domains.address.repositories.*
import com.example.servicito.domains.address.services.AddressService
import com.example.servicito.domains.buildings.models.entities.Building
import com.example.servicito.domains.buildings.repositories.BuildingRepository
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class AddressServiceImpl @Autowired constructor(
        private val addressRepo: AddressRepo,
        private val divisionRepo: DivisionRepo,
        private val districtRepo: DistrictRepo,
        private val upazilaRepo: UpazilaRepo,
        private val unionRepo: UnionRepo,
        private val villageRepo: VillageRepo,
        private val buildingRepo: BuildingRepository
) : AddressService {

    @Transactional
    override fun convertAndSaveFromBuildings(buildings: List<Building>) {
        val divisions = this.divisionRepo.findAll()
        val districts = this.districtRepo.findAll()

        buildings.forEach {

            println("Updating address for building: ${it.id}")
            val addrOld = it.addressOld

            val addr = Address()
            addr.flat = addrOld.flat
            addr.floor = addrOld.floor
            addr.house = addrOld.house
            addr.road = addrOld.road
            addr.postCode = addrOld.postCode
            addr.area = addrOld.area

            addr.division = divisions.firstOrNull { div ->
                addrOld.district.contains(div.nameEn ?: "", ignoreCase = true)
                        || addrOld.district.contains(div.nameBn ?: "", ignoreCase = true)
                        || Commons.similarity(div.nameEn, addrOld.division ?: "") > 0
                        || Commons.similarity(div.nameBn, addrOld.division ?: "") > 0
                        || Commons.similarity(div.nameEn, addrOld.district) > 0.5
                        || Commons.similarity(div.nameBn, addrOld.district) > 0.5
            }

            addr.district = districts.firstOrNull { dis ->
                dis.nameEn?.contains(addrOld.district, ignoreCase = true) ?: false
                        || addrOld.district.contains(dis?.nameEn ?: "", ignoreCase = true)
                        || dis.nameBn?.contains(addrOld.district, ignoreCase = true) ?: false
                        || addrOld.district.contains(dis?.nameBn ?: "", ignoreCase = true)
                        || Commons.similarity(dis.nameEn, addrOld.district ?: "") > 0.5
                        || Commons.similarity(dis.nameBn, addrOld.district ?: "") > 0.5
            }
            if (addr.district != null) addr.division = addr.district!!.division
//            addr.district = this.districtRepo.search(addrOld.district, addr.division).firstOrNull()

            var upazilas = this.upazilaRepo.search(addrOld.policeStation, addr.district)
            if (upazilas.isEmpty() && addrOld.upazila != null) upazilas = this.upazilaRepo.search(addrOld.upazila
                    ?: "", addr.district)
            if (upazilas.isNotEmpty())
                addr.upazila = upazilas.first()
            else {
                val thana = Upazila()
                thana.nameEn = addrOld.policeStation
                thana.nameBn = addrOld.policeStation
                thana.district = addr.district
                thana.thana = true
                addr.upazila = this.upazilaRepo.save(thana)
            }

            if (addrOld.union != null) {
                val unions = this.unionRepo.search(addrOld.union, addr.upazila)
                if (unions.isNotEmpty()) addr.union = unions.first()
                else if (addr.upazila != null) {
                    val union = Union()
                    union.nameEn = addrOld.union
                    union.nameBn = addrOld.union
                    union.upazila = addr.upazila
                    addr.union = this.unionRepo.save(union)
                }
            }

            if (addrOld.village != null) {
                val villages = this.villageRepo.search(addrOld.village, addr.union)
                if (villages.isNotEmpty()) addr.village = villages.first()
                else if (addr.union != null) {
                    val village = Village()
                    village.nameEn = addrOld.village
                    village.nameBn = addrOld.village
                    village.union = addr.union
                    addr.village = this.villageRepo.save(village)
                }
            }

            addr.latLng = it.latlngOld

            println("Saving address: Div: ${addr.division?.nameEn}, District: ${addr.district?.nameEn}, Upazila: ${addr.upazila?.nameEn}")
            it.address = this.addressRepo.save(addr)
            println("Address saved! Updating Building..")
            it.addressFixed = true
            this.buildingRepo.save(it)
            println("Updated!!\n")
        }
    }

    override fun search(query: String, page: Int, size: Int): Page<Address> {
        return this.addressRepo.search(query, PageAttr.getPageRequest(page))
    }

    override fun save(entity: Address): Address {
        return this.addressRepo.save(entity)
    }

    override fun find(id: Long): Optional<Address> {
        return this.addressRepo.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (!softDelete) {
            this.addressRepo.deleteById(id)
            return
        }
        val addr: Address = this.find(id).orElseThrow { NotFoundException("Could not find address with id $id") }
        addr.isDeleted = true
        this.save(addr)
    }

}
