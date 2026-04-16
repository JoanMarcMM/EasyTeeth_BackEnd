package com.easyteeth.EasyTeeth.controller;

import java.time.LocalDate;
import java.util.List;

public class UtensilOrderRequest {

    private LocalDate orderDate;
    private Long storageId;
    private List<OrderItemRequest> orderItems;

    public UtensilOrderRequest() {
    }

    public UtensilOrderRequest(LocalDate orderDate, Long storageId, List<OrderItemRequest> orderItems) {
        this.orderDate = orderDate;
        this.storageId = storageId;
        this.orderItems = orderItems;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
