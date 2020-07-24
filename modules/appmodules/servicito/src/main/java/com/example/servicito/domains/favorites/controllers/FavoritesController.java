package com.example.servicito.domains.favorites.controllers;

import com.example.auth.annotations.CurrentUser;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.favorites.services.FavoriteService;
import com.example.auth.entities.User;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/favorites")
public class FavoritesController {
    private final FavoriteService favoriteService;

    @Autowired
    public FavoritesController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // GET FAVORITE APARTMENTS
    @GetMapping("")
    private ResponseEntity getFavoriteApartments(@CurrentUser User currentUser) {
        Page<Apartment> apartmentPage = this.favoriteService.getFavoriteApartments(currentUser.getId());
        return ResponseEntity.ok(apartmentPage);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity toggleFavorites(@RequestParam("apartmentId") Long apartmentId,
                                           @CurrentUser User currentUser) throws UserNotFoundException, InvalidException, NotFoundException, com.example.common.exceptions.notfound.NotFoundException {
        boolean isFavorite = this.favoriteService.toggleFavorites(apartmentId, currentUser.getId());
        return ResponseEntity.ok(isFavorite);
    }

    @GetMapping("/{id}/isFavorite")
    private ResponseEntity isFavorite(@PathVariable("id") Long apartmentId,
                                      @CurrentUser User currentUser) {
        boolean isFavorite = this.favoriteService.isFavorite(apartmentId, currentUser.getId());
        return ResponseEntity.ok(isFavorite);
    }

}
