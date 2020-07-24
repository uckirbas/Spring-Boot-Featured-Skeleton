package com.example.servicito.domains.favorites.models.entities;

import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.coreweb.domains.base.entities.BaseEntity;
import com.example.auth.entities.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private Apartment apartment;

    public Favorite() {
    }

    public Favorite(User user, Apartment apartment) {
        this.user = user;
        this.apartment = apartment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
