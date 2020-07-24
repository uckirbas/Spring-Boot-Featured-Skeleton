package com.example.servicito.domains.buildings.repositories

import com.example.servicito.domains.buildings.models.entities.Building
import com.example.auth.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface BuildingRepository : JpaRepository<Building, Long> {
    @Query("SELECT b FROM Building b WHERE (:q IS NULL OR b.name LIKE %:q%) AND b.deleted=false")
    fun search(@Param("q") query: String, pageable: Pageable): Page<Building>

    @Query("SELECT b FROM Building b WHERE b.landlord=:landlord AND b.deleted=false")
    fun findByLandlord(@Param("landlord") landlord: User, pageable: Pageable): Page<Building>

    @Query("SELECT b FROM Building b WHERE b.id=:id AND b.deleted=false")
    fun find(@Param("id") id: Long): Optional<Building>

    @Query("SELECT COUNT(b) FROM Building b WHERE b.landlord.id=:landlordId AND b.deleted=false")
    fun count(@Param("landlordId") landlordId: Long): Int

    fun countByCreatedByUsernameAndCreatedBetweenAndVerifiedTrue(username: String, fromDate: Date, toDate: Date): Int

    fun countByCreatedByUsernameAndCreatedAndVerifiedTrue(username: String, createdAt: Date): Int

    fun countByCreatedBetween(fromDate: Date, toDate: Date): Int

    @Query("SELECT b FROM Building b WHERE b.addressFixed = FALSE")
    fun findNotFixed(pageable: Pageable): Page<Building>
}