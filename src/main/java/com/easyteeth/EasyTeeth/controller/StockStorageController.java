package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.StockStorage;
import com.easyteeth.EasyTeeth.controller.StockReductionRequest;
import com.easyteeth.EasyTeeth.controller.ItemReductionRequest;
import com.easyteeth.EasyTeeth.model.Storage;
import com.easyteeth.EasyTeeth.model.Utensil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UtensilRepository utensilRepository;

    @Autowired
    private StorageRepository storageRepository;

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
            System.out.println("🔻 REDUCE REQUEST: boxId=" + request.getBoxId() + ", date=" + request.getDate());
            System.out.println("   Items to reduce: " + request.getItems().size());
            
            for (ItemReductionRequest item : request.getItems()) {
                Long utensilId = item.getUtensilId();
                int quantityToReduce = item.getQuantity();
                int remainingToReduce = quantityToReduce;

                System.out.println("   - Reducing utensil " + utensilId + " x" + quantityToReduce);

                List<StockStorage> storagesWithUtensil = stockStorageRepository.findByUtensilId(utensilId);

                for (StockStorage stock : storagesWithUtensil) {
                    if (remainingToReduce <= 0) {
                        break;
                    }

                    int currentQuantity = stock.getQuantity();
                    
                    if (currentQuantity >= remainingToReduce) {
                        stock.setQuantity(currentQuantity - remainingToReduce);
                        stockStorageRepository.save(stock);
                        System.out.println("     ✓ Reduced in storage " + stock.getStorage().getId() + ": " + currentQuantity + " -> " + stock.getQuantity());
                        remainingToReduce = 0;
                    } else {
                        stock.setQuantity(0);
                        stockStorageRepository.save(stock);
                        System.out.println("     ✓ Emptied storage " + stock.getStorage().getId() + ": " + currentQuantity + " -> 0");
                        remainingToReduce -= currentQuantity;
                    }
                }

                if (remainingToReduce > 0) {
                    System.out.println("     ✗ Insufficient stock! Missing: " + remainingToReduce);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Insufficient stock for utensil ID " + utensilId + ". Missing: " + remainingToReduce);
                }
            }

            System.out.println("✓ REDUCE COMPLETED");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("✗ ERROR REDUCING STOCK: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error reducing stock: " + e.getMessage());
        }
    }

    @PostMapping("/restore")
    public ResponseEntity<?> restoreStockFromBox(@RequestBody StockReductionRequest request) {
        try {
            System.out.println("🔄 RESTORE REQUEST: boxId=" + request.getBoxId() + ", date=" + request.getDate());
            System.out.println("   Items to restore: " + request.getItems().size());
            
            for (ItemReductionRequest item : request.getItems()) {
                Long utensilId = item.getUtensilId();
                int quantityToRestore = item.getQuantity();
                
                System.out.println("   - Restoring utensil " + utensilId + " x" + quantityToRestore);

                List<StockStorage> storagesWithUtensil = stockStorageRepository.findByUtensilId(utensilId);
                System.out.println("     Found " + storagesWithUtensil.size() + " storages with this utensil");

                if (storagesWithUtensil.isEmpty()) {
                    System.out.println("     No storages found, creating new entry...");
                    List<Storage> allStorages = storageRepository.findAll();
                    System.out.println("     Total storages available: " + allStorages.size());
                    
                    if (!allStorages.isEmpty()) {
                        Utensil utensil = utensilRepository.findById(utensilId).orElse(null);
                        if (utensil != null) {
                            StockStorage newStock = new StockStorage();
                            newStock.setUtensil(utensil);
                            newStock.setStorage(allStorages.get(0));
                            newStock.setQuantity(quantityToRestore);
                            
                            StockStorage saved = stockStorageRepository.save(newStock);
                            System.out.println("     ✓ Created new stock entry: id=" + saved.getId() + ", quantity=" + saved.getQuantity());
                        } else {
                            System.out.println("     ✗ Utensil not found!");
                        }
                    } else {
                        System.out.println("     ✗ No storages available!");
                    }
                } else {
                    StockStorage firstStorage = storagesWithUtensil.get(0);
                    int currentQty = firstStorage.getQuantity();
                    int newQty = currentQty + quantityToRestore;
                    
                    System.out.println("     Adding to storage " + firstStorage.getStorage().getId());
                    System.out.println("     Current: " + currentQty + " + Restore: " + quantityToRestore + " = New: " + newQty);
                    
                    firstStorage.setQuantity(newQty);
                    StockStorage saved = stockStorageRepository.save(firstStorage);
                    System.out.println("     ✓ Updated stock: quantity=" + saved.getQuantity());
                }
            }

            System.out.println("✓ RESTORE COMPLETED");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("✗ ERROR RESTORING STOCK: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error restoring stock: " + e.getMessage());
        }
    }

}