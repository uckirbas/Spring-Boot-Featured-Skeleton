package com.example.servicito.domains.address.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "addr_addresses")
class Address : BaseEntity() {

    @Column(name = "flat")
    var flat: String? = null

    @Column(name = "floor", nullable = false)
    var floor: Byte = 0

    @Column(name = "house")
    var house: String? = null

    @Column(name = "road")
    var road: String? = null

    @Column(name = "post_code")
    var postCode: String? = null

    @Column(name = "area")
    lateinit var area: String

    @ManyToOne
    var division: Division? = null

    @ManyToOne
    var district: District? = null

    @ManyToOne
    var upazila: Upazila? = null

    @ManyToOne
    var union: Union? = null

    @ManyToOne
    var village: Village? = null

    @Embedded
    var latLng: LatLng? = null

}
