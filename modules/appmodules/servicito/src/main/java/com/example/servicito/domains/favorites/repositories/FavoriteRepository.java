package com.example.servicito.domains.favorites.repositories;

import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.favorites.models.entities.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByApartmentIdAndUserId(Long apartmentId,Long userId);
    Favorite findByApartmentIdAndUserId(Long apartmentId,Long userId);

    @Query("SELECT f.apartment FROM Favorite f WHERE f.user.id = :userId")
    Page<Apartment> findFavoriteApartmentsByUserId(@Param("userId") Long userId, Pageable pageable);
}
