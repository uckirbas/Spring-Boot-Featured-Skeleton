package com.example.servicito.domains.address.repositories

import com.example.servicito.domains.address.models.entities.Union
import com.example.servicito.domains.address.models.entities.Village
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VillageRepo : JpaRepository<Village, Long> {

    @Query("SELECT v FROM Village v WHERE (:q IS NULL OR v.nameEn LIKE %:q%) AND v.deleted = false")
    fun search(@Param("q") query: String, pageable: Pageable): Page<Village>

    @Query("SELECT v FROM Village v WHERE v.id = :id AND v.deleted = false")
    fun find(@Param("id") id: Long): Optional<Village>

    @Query("SELECT v FROM Village v WHERE (:q IS NULL OR (v.nameEn LIKE %:q% OR v.nameBn LIKE %:q%)) AND (:union IS NULL OR v.union=:union)")
    fun search(@Param("q") query: String, @Param("union") union: Union?): List<Village>
}
