package com.example.servicito.domains.favorites.services;

import com.example.common.exceptions.notfound.NotFoundException;
import com.example.coreweb.utils.PageAttr;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.apartments.services.ApartmentService;
import com.example.servicito.domains.favorites.models.entities.Favorite;
import com.example.servicito.domains.favorites.repositories.FavoriteRepository;
import com.example.auth.entities.User;
import com.example.acl.domains.users.services.UserService;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepo;
    private final ApartmentService apartmentService;
    private final UserService userService;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepo, ApartmentService apartmentService, UserService userService) {
        this.favoriteRepo = favoriteRepo;
        this.apartmentService = apartmentService;
        this.userService = userService;
    }

    @Override
    public Page<Apartment> getFavoriteApartments(Long userId) {
        return this.favoriteRepo.findFavoriteApartmentsByUserId(userId, PageAttr.getPageRequest(0));
    }

    @Override
    public Favorite save(Favorite favorite) throws NotFoundException, InvalidException {
        if (favorite == null) throw new NotFoundException("Could not find favorite");
        if (favorite.getUser() == null || favorite.getApartment() == null)
            throw new InvalidException("Apartment or user can not be null");
        return this.favoriteRepo.save(favorite);
    }

    @Override
    public Favorite findOne(Long id) throws NotFoundException {
        Favorite favorite = this.favoriteRepo.findById(id).orElse(null);
        if (favorite == null) throw new NotFoundException("Could not find favorite");
        return favorite;
    }

    @Override
    public Favorite findOne(Long apartmentId, Long userId) throws NotFoundException {
        Favorite favorite = this.favoriteRepo.findByApartmentIdAndUserId(apartmentId, userId);
        if (favorite == null) throw new NotFoundException("Could not find favorite");
        return favorite;
    }

    @Override
    public boolean toggleFavorites(Long apartmentId, Long userId) throws  NotFoundException, InvalidException {
        Favorite favorite;
        if (this.favoriteRepo.existsByApartmentIdAndUserId(apartmentId, userId)) {
            favorite = this.findOne(apartmentId, userId);
            this.favoriteRepo.delete(favorite);
            return false;
        }
        Apartment apartment = this.apartmentService.find(apartmentId).orElseThrow(() -> new NotFoundException("Couldn't find apartment with id:" + apartmentId));
        User user = this.userService.find(userId).orElseThrow(() -> new UserNotFoundException("Couldn't find user with id: " + userId));
        favorite = new Favorite(user, apartment);
        return this.save(favorite) != null;
    }

    @Override
    public boolean isFavorite(Long apartmentId, Long userId) {
        return this.favoriteRepo.existsByApartmentIdAndUserId(apartmentId, userId);
    }

    @Override
    public void removeFavorite(Long favoriteId) {
        this.favoriteRepo.deleteById(favoriteId);
    }
}
