package com.example.servicito.domains.locationhistory.repositories;

import com.example.servicito.domains.locationhistory.models.entities.LocationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long> {
    @Query("SELECT lh FROM LocationHistory lh WHERE lh.deleted=false")
    Page<LocationHistory> findAll(Integer page, Pageable pageable);

    @Query("SELECT lh FROM LocationHistory lh WHERE (:userId IS NULL OR lh.user.id=:userId ) AND (:q IS NULL OR (lh.user.username LIKE %:q% OR lh.user.name LIKE %:q%)) AND lh.deleted=false")
    Page<LocationHistory> search(@Param("userId") Long userId, @Param("q") String query, Pageable pageable);
}
