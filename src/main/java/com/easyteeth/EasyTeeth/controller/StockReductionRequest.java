package com.easyteeth.EasyTeeth.controller;

import java.util.List;

public class StockReductionRequest {
    private Long boxId;
    private String date;
    private List<ItemReductionRequest> items;

    public StockReductionRequest() {
    }

    public StockReductionRequest(Long boxId, String date, List<ItemReductionRequest> items) {
        this.boxId = boxId;
        this.date = date;
        this.items = items;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ItemReductionRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemReductionRequest> items) {
        this.items = items;
    }
}