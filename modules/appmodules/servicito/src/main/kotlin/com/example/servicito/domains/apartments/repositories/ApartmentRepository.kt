package com.example.servicito.domains.apartments.repositories

import com.example.servicito.domains.apartments.models.dtos.ApartmentSlice
import com.example.servicito.domains.apartments.models.entities.Apartment
import com.example.servicito.domains.statistics.models.dtos.DateCountPair
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ApartmentRepository : JpaRepository<Apartment, Long> {

    @Query("SELECT a FROM Apartment a WHERE (:q IS NULL OR a.apartmentNumber LIKE %:q%) AND a.deleted=false")
    fun search(@Param("q") query: String, pageable: Pageable): Page<Apartment>

    @Query("SELECT a FROM Apartment a WHERE a.id=:id AND a.deleted=false")
    fun find(@Param("id") id: Long): Optional<Apartment>

    @Query("SELECT a FROM Apartment a WHERE a.building.id=:buildingId AND a.deleted=false")
    fun findByBuilding(@Param("buildingId") buildingId: Long): List<Apartment>

    /*Removed GROUP BY FOR NOW */
    @Query(value = "SELECT `latitude`,`longitude`,`number_of_bed`,`rent`, `available`,`bachelor_allowed`,`verified`,`id`,`security_token` , ( 3959 * acos( cos( radians(:latitude) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( latitude ) ) ) ) AS distance FROM apartments WHERE available=TRUE AND deleted=FALSE HAVING distance < 5000 ORDER BY distance LIMIT 0 , 70", nativeQuery = true)
    fun findNearbyLatLng(@Param("latitude") latitude: Double, @Param("longitude") longitude: Double): List<Array<Any>>

    @Query(value = "SELECT `latitude`,`longitude`,`number_of_bed`,`rent`, `available`,`bachelor_allowed`,`verified`,`id`,`security_token` , ( 3959 * acos( cos( radians(:latitude) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( latitude ) ) ) ) AS distance FROM apartments WHERE available=TRUE AND deleted=FALSE  AND bachelor_allowed=:bachelor HAVING distance < 5000 ORDER BY distance LIMIT 0 , 70", nativeQuery = true)
    fun findNearbyLatLngBachelor(@Param("latitude") latitude: Double, @Param("longitude") longitude: Double, @Param("bachelor") bachelor: Boolean): List<Array<Any>>

    /*FILTER*/
    @Query(value = "SELECT `latitude`,`longitude`,`number_of_bed`,`rent`, `available`,`bachelor_allowed`,`verified`,`id`,`security_token` , ( 3959 * acos( cos( radians(:latitude) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( latitude ) ) ) ) AS distance FROM apartments " +
            "WHERE available=true AND " +
            "deleted=false AND " +
            "bachelor_allowed=:bachelor AND " +
            "number_of_bed=:bed AND " +
            "rent BETWEEN :rentFrom AND :rentTo " +
            "HAVING distance < 5000 ORDER BY distance LIMIT 0 , 70", nativeQuery = true)
    fun filterNearbyLatLngBachelor(
            @Param("latitude") latitude: Double,
            @Param("longitude") longitude: Double,
            @Param("bachelor") bachelor: Boolean,
            @Param("rentFrom") rentFrom: Int,
            @Param("rentTo") rentTo: Int,
            @Param("bed") bed: Int
    ): List<Array<Any>>

    @Query("SELECT COUNT(a) FROM Apartment a WHERE a.building.landlord.id=:userId AND a.deleted=false")
    fun countByLandlord(@Param("userId") userId: Long): Int

    @Query("SELECT COUNT(a) FROM Apartment a WHERE a.building.landlord.id=:userId AND a.available=:available AND a.deleted=false")
    fun countAvailableByLandlord(@Param("userId") userId: Long, @Param("available") available: Boolean): Int

    @Query("SELECT new com.servicito.webservice.domains.apartments.models.dtos.ApartmentSlice(a.id , a.apartmentNumber,a.totalVisitCount,a.visitCountAfterMarkedAvailable) FROM Apartment a WHERE a.visitCountAfterMarkedAvailable > 0 AND a.building.landlord.id=:userId AND a.deleted=false")
    fun findApartmentStats(@Param("userId") userId: Long?): List<ApartmentSlice>

    fun countByCreatedByUsernameAndCreatedBetweenAndVerifiedTrue(username: String, fromDate: Date, toDate: Date): Int

    fun countByCreatedByUsernameAndCreatedAndVerifiedTrue(username: String?, createdAt: Date?): Int

    fun countByCreatedBetween(fromDate: Date?, toDate: Date?): Int

    @Query("SELECT new com.servicito.webservice.domains.statistics.models.dtos.DateCountPair(u.created, count(u.id)) FROM Apartment u WHERE (u.created BETWEEN :fromDate AND :toDate) AND u.deleted=false")
    fun countApartmentsInPeriod(@Param("fromDate") fromDate: Date, @Param("toDate") toDate: Date): List<DateCountPair?>?

    @Query("SELECT a FROM Apartment a WHERE ( a.created BETWEEN :fromDate AND :toDate) AND a.deleted=false")
    fun findWithinPeriod(@Param("fromDate") fromDate: Date, @Param("toDate") toDate: Date): List<Apartment>

}