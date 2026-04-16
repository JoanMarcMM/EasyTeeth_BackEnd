package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.StockStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stockStorage")
public class StockStorageController {

    private StockStorageRepository stockStorageRepository;

    public StockStorageController(StockStorageRepository stockStorageRepository) {
        this.stockStorageRepository = stockStorageRepository;
    }

    @GetMapping("/storage/{storageId}")
    public ResponseEntity<List<StockStorage>> getStockByStorage(@PathVariable("storageId") Long storageId)
            throws IOException {
        List<StockStorage> stocks = stockStorageRepository.findByStorageId(storageId);
        return ResponseEntity.ok(stocks);
    }
}
