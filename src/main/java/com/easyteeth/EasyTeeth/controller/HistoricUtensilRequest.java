package com.easyteeth.EasyTeeth.controller;

import java.time.LocalDateTime;

public class HistoricUtensilRequest {

    private String name;
    private String brand;
    private String model;
    private double price;
    private Long supplierId;
    private LocalDateTime dateAdded;

    public HistoricUtensilRequest() {
    }

    public HistoricUtensilRequest(String name, String brand, String model, double price, Long supplierId) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }
}
