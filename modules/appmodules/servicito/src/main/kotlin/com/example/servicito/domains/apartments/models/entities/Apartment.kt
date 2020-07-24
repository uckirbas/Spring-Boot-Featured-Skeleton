package com.example.servicito.domains.apartments.models.entities

import com.example.common.utils.DateUtil
import com.example.servicito.domains.apartments.models.embedables.Charge
import com.example.servicito.domains.buildings.models.entities.Building
import com.example.coreweb.domains.base.entities.BaseEntity
import com.example.auth.entities.User
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "apartments")
//@JsonIgnoreProperties("hibernateLazyInitializer", "handler")
class Apartment : BaseEntity() {

    @Column(name = "apartment_number", nullable = false)
    lateinit var apartmentNumber: String

    @Column(name = "number_of_bed")
    var numberOfBed: Byte = 0

    @Column(name = "number_of_bath")
    var numberOfBath: Byte = 0

    @Column(name = "kitchen")
    var hasKitchen = false

    @Column(name = "dining")
    var hasDining = false

    @Column(name = "drawing")
    var hasDrawing = false

    @Column(name = "number_of_balcony")
    var numberOfBalcony: Byte = 0

    @Column(name = "floor_number")
    var floorNumber: Byte = 0

    @Column(name = "gas")
    var hasGas = false

    @Column(name = "water")
    var hasWater = false

    @Column(name = "electricity")
    var hasElectricity = false

    @Column(name = "roof_access")
    var hasRoofAccess = false

    @Column(name = "geyser")
    var hasGeyser = false

    var tiled = false

    var furnished = false

    @Column(name = "window_type")
    var windowType: String? = null

    var faced: String? = null

    var description: String? = null

    @Column(name = "civil_design", columnDefinition = "TEXT")
    var civilDesign: String? = null

    var verified = false

    @Column(name = "available_from")
    var availableFrom: Date? = null

    @Column(name = "apartment_size")
    var apartmentSize = 0

    var available = false

    @Column(name = "video_link")
    var videoLink: String? = null

    @Column(name = "bachelor_allowed", nullable = false)
    var bachelorAllowed: Boolean = false

    @Column(name = "total_visit_count")
    var totalVisitCount: Long = 0

    @Column(name = "visit_count_after_marked_available")
    var visitCountAfterMarkedAvailable: Long = 0

    @Column(name = "security_token")
    lateinit var securityToken: String

    var fixed = false

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(CascadeType.ALL)
    lateinit var images: MutableList<String>

    @Embedded
    lateinit var charges: Charge

    @ManyToOne
    var infoCollectedBy: User? = null

    @ManyToOne
    lateinit var building: Building

    fun firstImageUrl(): String {
        if (this.images.isEmpty())
            return ""
        return this.images[0]
    }

    fun hasAuthorizedAccessFor(user: User): Boolean {
        return building.hasAuthorizedAccessFor(user)
    }

    fun addImagePath(imagePath: String) {
        images.add(imagePath)
    }

    fun increaseVisitCounts() {
        totalVisitCount++
        visitCountAfterMarkedAvailable++
    }

    fun getAvailableDateReadable(): String {
        return if (availableFrom == null) "" else DateUtil.getReadableDate(availableFrom)
    }

    fun getLink(): String {
        return "/apartments/$id/$securityToken"
    }

    fun hasBalcony(): Boolean {
        return numberOfBalcony > 0
    }

//    fun getDescription(): String? {
//        return if (description == null || description!!.trim().isEmpty())
//            "No description is provided yet." else description
//    }

}