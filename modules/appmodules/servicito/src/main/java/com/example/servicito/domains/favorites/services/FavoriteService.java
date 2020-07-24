package com.example.servicito.domains.favorites.services;

import com.example.common.exceptions.notfound.NotFoundException;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.favorites.models.entities.Favorite;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import org.springframework.data.domain.Page;

public interface FavoriteService {

    Page<Apartment> getFavoriteApartments(Long userId);

    Favorite save(Favorite favorite) throws NotFoundException, InvalidException;

    Favorite findOne(Long id) throws NotFoundException;

    Favorite findOne(Long apartmentId, Long userId) throws NotFoundException;

    boolean toggleFavorites(Long apartmentId, Long userId) throws UserNotFoundException, NotFoundException, InvalidException;

    boolean isFavorite(Long apartmentId, Long userId);

    void removeFavorite(Long favoriteId);

}
