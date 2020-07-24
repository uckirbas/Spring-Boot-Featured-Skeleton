package com.example.servicito.domains.address.models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Embeddable
public class AddressOld {
    @Column(name = "flat")
    private String flat;

    @Column(name = "floor")
    private byte floor;

    @Column(name = "house")
    private String house;

    @Column(name = "road")
    private String road;

    @Column(name = "area",nullable = false)
    @NotNull
    private String area;

    @Column(name = "village")
    private String village;

    @Column(name = "post_office")
    private String postOffice;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "police_station",nullable = false)
    @NotNull
    private String policeStation;

    @Column(name = "addr_union")
    private String union;

    @Column(name = "upazila")
    private String upazila;

    @Column(name="district",nullable = false)
    @NotNull
    private String district;

    @Column(name = "division")
    private String division;

    @Column(name = "country")
    private String country;

    @PrePersist
    private void onPrePersist() {
        if (this.country == null || this.country.isEmpty())
            this.country = "Bangladesh";
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public byte getFloor() {
        return floor;
    }

    public void setFloor(byte floor) {
        this.floor = floor;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getUpazila() {
        return upazila;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "area: " + area +
                ", policeStation: " + policeStation +
                ", union: " + union +
                ", upazila: " + upazila;
    }
}

