package com.example.servicito.domains.histories.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.coreweb.domains.base.entities.BaseEntity;
import com.example.auth.entities.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "rent_histories")
public class RentHistory extends BaseEntity{
    @ManyToOne
    @JsonManagedReference
    private User user;
    @ManyToOne
    private Apartment apartment;
    private Date startDate;
    private Date endDate;
    private boolean ended;

    @PreUpdate
    private void onPreUpdate(){
        this.setEnded(this.endDate!=null);
    }
    public RentHistory(){}
    public RentHistory(User user, Apartment apartment, Date startDate) {
        this.user = user;
        this.apartment = apartment;
        this.startDate = startDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
