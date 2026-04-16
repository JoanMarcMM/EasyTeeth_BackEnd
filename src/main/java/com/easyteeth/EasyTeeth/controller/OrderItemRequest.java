package com.easyteeth.EasyTeeth.controller;

public class OrderItemRequest {

    private Long orderId;
    private Long utensilId;
    private Integer quantity;
    private Double unitPrice;

    public OrderItemRequest() {
    }

    public OrderItemRequest(Long orderId, Long utensilId, Integer quantity, Double unitPrice) {
        this.orderId = orderId;
        this.utensilId = utensilId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUtensilId() {
        return utensilId;
    }

    public void setUtensilId(Long utensilId) {
        this.utensilId = utensilId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
