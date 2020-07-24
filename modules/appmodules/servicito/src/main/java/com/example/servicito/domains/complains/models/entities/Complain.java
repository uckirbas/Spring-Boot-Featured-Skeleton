package com.example.servicito.domains.complains.models.entities;

import com.example.auth.config.security.SecurityContext;
import com.example.servicito.domains.apartments.models.entities.Apartment;
import com.example.coreweb.domains.base.entities.BaseEntity;
import com.example.auth.entities.UserAuth;
import com.example.auth.entities.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "complains")
public class Complain extends BaseEntity {
    private String message;
    private boolean resolved;

    @ManyToOne
    private Apartment apartment;
    @ManyToOne
    private User complainedBy;

    @ManyToOne
    private User resolvedBy;

    @PrePersist
    private void onPrePersist() {
        UserAuth loggedInUser = SecurityContext.getCurrentUser();
        if (loggedInUser != null)
            this.complainedBy = new User(loggedInUser);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getComplainedBy() {
        return complainedBy;
    }

    public void setComplainedBy(User complainedBy) {
        this.complainedBy = complainedBy;
    }

    public User getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(User resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
