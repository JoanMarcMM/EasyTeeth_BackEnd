package com.easyteeth.EasyTeeth.controller;

import java.util.List;

public class OdontologistRequest {

    private String name;
    private String lastname1;
    private String lastname2;
    private String dni;
    private String licenseNumber;
    private List<Long> specialityIds;
    private List<Long> availabilityIds;

    public OdontologistRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname1() {
        return lastname1;
    }

    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    public String getLastname2() {
        return lastname2;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public List<Long> getSpecialityIds() {
        return specialityIds;
    }

    public void setSpecialityIds(List<Long> specialityIds) {
        this.specialityIds = specialityIds;
    }

    public List<Long> getAvailabilityIds() {
        return availabilityIds;
    }

    public void setAvailabilityIds(List<Long> availabilityIds) {
        this.availabilityIds = availabilityIds;
    }
}