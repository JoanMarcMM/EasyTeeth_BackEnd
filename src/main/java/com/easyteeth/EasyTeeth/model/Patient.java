package com.easyteeth.EasyTeeth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String lastname1;
    String lastname2;
    String ssn;
    String dni;

    String phoneNumber;
    String email;

    String billingAddress;
    String bankAccountNumber;
    String taxIdentificationNumber;

    @Transient
    private boolean isContagious;

    @Transient
    private boolean hasAllergies;

    public Patient() {

    }

    public Patient(String name, String lastname1, String lastname2, String ssn, String dni,
                   String phoneNumber, String email, String billingAddress,
                   String bankAccountNumber, String taxIdentificationNumber) {
        super();
        this.name = name;
        this.lastname1 = lastname1;
        this.lastname2 = lastname2;
        this.ssn = ssn;
        this.dni = dni;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.billingAddress = billingAddress;
        this.bankAccountNumber = bankAccountNumber;
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public boolean isContagious() {
        return isContagious;
    }

    public void setContagious(boolean contagious) {
        isContagious = contagious;
    }

    public boolean isHasAllergies() {
        return hasAllergies;
    }

    public void setHasAllergies(boolean hasAllergies) {
        this.hasAllergies = hasAllergies;
    }
}
