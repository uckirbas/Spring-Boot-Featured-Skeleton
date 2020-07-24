package com.example.servicito.domains.requests.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.example.common.utils.DateUtil;
import com.example.coreweb.domains.base.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {
    private Date date;
    private String place;

    @JsonBackReference
    @OneToOne
    private MRequest request;

    public Appointment() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public MRequest getRequest() {
        return request;
    }

    public void setRequest(MRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "Date: " + DateUtil.getReadableDateTime(date) +
                "\nPlace: " + place;
    }
}
