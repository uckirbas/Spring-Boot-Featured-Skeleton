package com.example.servicito.domains.locationhistory.models.entities;

import com.example.coreweb.domains.base.entities.BaseEntity;
import com.example.auth.entities.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "location_history")
public class LocationHistory extends BaseEntity {
    private double latitude;
    private double longitude;

    private String reference;

    @ManyToOne
    private User user;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
