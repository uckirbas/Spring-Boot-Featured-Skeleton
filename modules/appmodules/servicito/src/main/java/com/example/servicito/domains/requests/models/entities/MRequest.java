package com.example.servicito.domains.requests.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.example.servicito.domains.address.models.entities.Address;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.servicito.domains.buildings.models.pojo.LandlordInfo;
import com.example.coreweb.domains.base.entities.BaseEntity;
import com.example.auth.entities.UserAuth;
import com.example.auth.entities.User;

import javax.persistence.*;

@Entity
@Table(name = "m_requests")
public class MRequest extends BaseEntity {
    private String status;
    @Column(columnDefinition = "LONGTEXT")
    private String note;
    @ManyToOne
    private User requestedBy;
    @ManyToOne
    private User requestedTo; // for special cases other than home renting
    @ManyToOne
    private User assignedEmployee;
    @ManyToOne
    private Apartment apartment;

    @JsonManagedReference
    @OneToOne(mappedBy = "request")
    private Appointment appointment;

    @PrePersist
    private void onPrePersist() {
        if (this.requestedBy == null) {
            UserAuth loggedInUser = getCurrentUser();
            if (loggedInUser != null)
                this.requestedBy = new User(loggedInUser);
        }
        if (requestedTo == null)
            this.requestedTo = apartment.building.landlord;
        if (this.status == null)
            this.status = Status.PENDING.getValue();
    }

    public enum Status {
        PENDING("Pending"),
        RECEIVED("Received"),
        APPOINTMENT_FIXED("Appointment Fixed"),
        CONFIRMED("Confirmed"),
        CANCELED("Canceled");

        private String value;

        Status(String value) {
            this.value = value;
        }

        public static Status fromValue(String value) {
            if (value == null) value = "";
            for (Status status : Status.values()) {
                if (status.getValue().toLowerCase().equals(value.toLowerCase()))
                    return status;
            }
            return Status.PENDING;
        }

        public String getValue() {
            return value;
        }
    }

    public User getRequestedTo() {
        return requestedTo;
    }

    public void setRequestedTo(User requestedTo) {
        this.requestedTo = requestedTo;
    }

    public boolean isCancelable() {
        return !this.equalsStatus(Status.CANCELED);
    }

    public Address getBuildingAddress() {
        if (this.apartment == null) return null;
        return this.apartment.getBuilding().getAddress();
    }

    public LandlordInfo getLandlordInfo() {
        if (this.apartment == null) return null;
        User landlord = this.apartment.building.landlord;
        return new LandlordInfo(landlord.getId(), landlord.getName(), landlord.getPhone());
    }

    @JsonIgnore
    public User getLandlord() {
        if (this.apartment == null) return null;
        return this.apartment.building.landlord;
    }

    public boolean isMarkedAsConfirmed() {
        return this.equalsStatus(Status.CONFIRMED);
    }

    public boolean isAppointmentFixed() {
        return this.equalsStatus(Status.APPOINTMENT_FIXED);
    }

    public boolean isReceived() {
        return this.equalsStatus(Status.RECEIVED);
    }

    public boolean isPending() {
        return this.equalsStatus(Status.PENDING);
    }

    public boolean isCanceled() {
        return this.equalsStatus(Status.CANCELED);
    }


    public boolean equalsStatus(Status status) {
        return this.status != null && this.status.equals(status.getValue());
    }


    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }


    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(User assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }
}
