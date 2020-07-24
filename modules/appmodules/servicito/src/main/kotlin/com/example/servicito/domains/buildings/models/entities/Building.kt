package com.example.servicito.domains.buildings.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.example.servicito.domains.address.models.entities.LatLng
import com.example.servicito.domains.address.models.entities.Address
import com.example.servicito.domains.address.models.entities.AddressOld
import com.example.coreweb.domains.base.entities.BaseEntity
import com.example.auth.entities.User
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "buildings")
@JsonIgnoreProperties("hibernateLazyInitializer", "handler")
class Building : BaseEntity() {

    @Column(nullable = false)
    lateinit var name: String

    @OneToOne
    lateinit var address: Address

    @Embedded
    lateinit var addressOld: AddressOld

    @Embedded
    lateinit var latlngOld: LatLng

    var landmarks: String? = null

    @Column(name = "distance_from_busstand")
    var distanceFromBusstand: String? = null

    @Column(name = "nearby_hospitals")
    var nearbyHospitals: String? = null

    @Column(name = "nearby_police_station")
    var nearbyPoliceStation: String? = null

    @Column(name = "nearby_schools")
    var nearbySchools: String? = null

    @Column(name = "available_transportations")
    var availableTransportations: String? = null

    @Column(name = "area_type")
    var areaType: String? = null

    var hasLift: Boolean = false

    @Column(name = "security_type")
    var securityType: String? = null

    @Column(name = "marketplaces")
    var marketPlaces: String? = null

    @Column(name = "total_number_of_floor")
    var totalNumberOfFloor: Byte = 0

    @Column(name = "total_number_of_flat")
    var totalNumberOfFlat: Int = 0

    var verified: Boolean = false

    @Column(name = "video_link")
    var videoLink: String? = null

    @ElementCollection
    @CollectionTable(name = "building_image_paths")
    var imagePaths: MutableList<String>? = null

    @ManyToOne
    @JsonIgnore
    lateinit var landlord: User

    @ManyToOne
    @JsonIgnore
    lateinit var infoCollectedBy: User


    // FIXME: REMOVE AFTER MIGRATION/ADDRESS IS FIXED
    @Column(name = "address_fixed")
    var addressFixed: Boolean = false

    fun hasAuthorizedAccessFor(user: User): Boolean {
        return belongsTo(user) || user.isAdmin()
    }

    fun belongsTo(user: User): Boolean {
        return user.id == landlord.id
    }

    fun addImagePath(imagePath: String) {
        if (imagePaths == null) imagePaths = ArrayList()
        imagePaths?.add(imagePath)
    }
}