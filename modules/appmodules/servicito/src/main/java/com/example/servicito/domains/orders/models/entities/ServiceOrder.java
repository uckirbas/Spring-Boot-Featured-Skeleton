package com.example.servicito.domains.orders.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.coreweb.domains.base.entities.BaseEntity;
import com.example.auth.entities.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "service_orders")
public class ServiceOrder extends BaseEntity {
    @NotNull
    private String serviceType;
    private String serviceFrom;
    @NotNull
    private String serviceLocation;
    private Date date;
    @NotNull
    private String phoneNumber;
    private int charge;
    private String status;
    private String description;

    @ManyToOne
    private User user;

    @ManyToOne
    private User assignedEmployee;

    public boolean equalsStatus(Status status) {
        return status != null && status.toString().trim().toLowerCase().equals(this.status.trim().toLowerCase());
    }

    public enum Status {
        RECEIVED,
        CALLED,
        PROPOSED,
        CONFIRMED,
        PROCESSING,
        COMPLETED,
        CANCELED
    }

    public enum Type {
        HOME_SHIFTING("home_shifting"),
        INTERIOR_DESIGN("interior_design");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @JsonIgnore
    public boolean hasAuthorizedAccessFor(User user) {
        if (user == null) return false;
        return this.belongsTo(user)
                || user.isAdmin();
    }

    @JsonIgnore
    public boolean belongsTo(User user) {
        return user != null && this.user != null
                && user.getId().equals(this.user.getId());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(User assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceFrom() {
        return serviceFrom;
    }

    public void setServiceFrom(String serviceFrom) {
        this.serviceFrom = serviceFrom;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.RECEIVED.toString().toLowerCase();
        if (status != null)
            for (Status s : Status.values()) {
                if (s.toString().toLowerCase().equals(status.toLowerCase()))
                    this.status = status.trim().toLowerCase();
            }
    }

    @Override
    public String toString() {
        return "ServiceOrder{" +
                "serviceType='" + serviceType + '\'' +
                ", serviceFrom='" + serviceFrom + '\'' +
                ", serviceLocation='" + serviceLocation + '\'' +
                ", date=" + date +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", charge=" + charge +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", assignedEmployee=" + assignedEmployee +
                '}';
    }
}
