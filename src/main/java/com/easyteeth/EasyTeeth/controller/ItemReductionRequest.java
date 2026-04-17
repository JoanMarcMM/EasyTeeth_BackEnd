package com.easyteeth.EasyTeeth.controller;

public class ItemReductionRequest {
    private Long utensilId;
    private int quantity;

    public ItemReductionRequest() {
    }

    public ItemReductionRequest(Long utensilId, int quantity) {
        this.utensilId = utensilId;
        this.quantity = quantity;
    }

    public Long getUtensilId() {
        return utensilId;
    }

    public void setUtensilId(Long utensilId) {
        this.utensilId = utensilId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}