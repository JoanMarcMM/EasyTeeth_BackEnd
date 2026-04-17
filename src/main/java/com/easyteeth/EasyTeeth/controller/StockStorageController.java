package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.StockStorage;
import com.easyteeth.EasyTeeth.controller.StockReductionRequest;
import com.easyteeth.EasyTeeth.controller.ItemReductionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/reduce")
    public ResponseEntity<?> reduceStockForBox(@RequestBody StockReductionRequest request) {
        try {
            // For each utensil that needs to be reduced
            for (ItemReductionRequest item : request.getItems()) {
                Long utensilId = item.getUtensilId();
                int quantityToReduce = item.getQuantity();
                int remainingToReduce = quantityToReduce;

                // Find all storages that have this utensil
                List<StockStorage> storagesWithUtensil = stockStorageRepository.findByUtensilId(utensilId);

                // Reduce from each storage until we've reduced the total amount needed
                for (StockStorage stock : storagesWithUtensil) {
                    if (remainingToReduce <= 0) {
                        break;
                    }

                    int currentQuantity = stock.getQuantity();
                    
                    if (currentQuantity >= remainingToReduce) {
                        // This storage has enough, reduce what we need
                        stock.setQuantity(currentQuantity - remainingToReduce);
                        stockStorageRepository.save(stock);
                        remainingToReduce = 0;
                    } else {
                        // This storage doesn't have enough, reduce it to 0 and continue to next storage
                        stock.setQuantity(0);
                        stockStorageRepository.save(stock);
                        remainingToReduce -= currentQuantity;
                    }
                }

                // Check if we had enough stock
                if (remainingToReduce > 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Insufficient stock for utensil ID " + utensilId + ". Missing: " + remainingToReduce);
                }
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error reducing stock: " + e.getMessage());
        }
    }

}