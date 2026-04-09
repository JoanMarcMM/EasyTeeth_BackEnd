package com.easyteeth.EasyTeeth.controller;

import java.time.LocalDate;

public class StockBoxRequest {
    private Long utensilId;
    private Long boxId;
    private int quantity;
    private boolean stocked;
    private LocalDate day;

    public StockBoxRequest() {}

    public Long getUtensilId() {
        return utensilId;
    }

    public void setUtensilId(Long utensilId) {
        this.utensilId = utensilId;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStocked() {
        return stocked;
    }

    public void setStocked(boolean stocked) {
        this.stocked = stocked;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
}
