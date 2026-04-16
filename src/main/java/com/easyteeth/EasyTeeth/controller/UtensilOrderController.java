package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utensilOrder")
public class UtensilOrderController {

    private UtensilOrderRepository utensilOrderRepository;
    private OrderItemRepository orderItemRepository;
    private UtensilRepository utensilRepository;
    private StockStorageRepository stockStorageRepository;
    private StorageRepository storageRepository;

    public UtensilOrderController(
            UtensilOrderRepository utensilOrderRepository,
            OrderItemRepository orderItemRepository,
            UtensilRepository utensilRepository,
            StockStorageRepository stockStorageRepository,
            StorageRepository storageRepository
    ) {
        this.utensilOrderRepository = utensilOrderRepository;
        this.orderItemRepository = orderItemRepository;
        this.utensilRepository = utensilRepository;
        this.stockStorageRepository = stockStorageRepository;
        this.storageRepository = storageRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UtensilOrder>> getOrder(@PathVariable("id") Long orderId)
            throws IOException {
        Optional<UtensilOrder> order = utensilOrderRepository.findById(orderId);
        if (order.isPresent()) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/index")
    public ResponseEntity<List<UtensilOrder>> getAll() throws IOException {
        List<UtensilOrder> orders = utensilOrderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/byArrived/{arrived}")
    public ResponseEntity<List<UtensilOrder>> getByArrived(@PathVariable("arrived") boolean arrived)
            throws IOException {
        List<UtensilOrder> orders = utensilOrderRepository.findByArrived(arrived);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createOrder(@RequestBody UtensilOrderRequest req) {
        try {
            if (req == null || req.getOrderDate() == null || req.getStorageId() == null || req.getOrderItems() == null || req.getOrderItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Faltan campos obligatorios o items");
            }

            Storage storage = storageRepository.findById(req.getStorageId()).orElse(null);
            if (storage == null) {
                return ResponseEntity.badRequest().body("Storage con id " + req.getStorageId() + " no existe");
            }

            UtensilOrder order = new UtensilOrder();
            order.setOrderDate(req.getOrderDate());
            order.setStorage(storage);
            order.setArrived(false);

            for (OrderItemRequest itemReq : req.getOrderItems()) {
                if (itemReq.getUtensilId() == null || itemReq.getQuantity() <= 0) {
                    return ResponseEntity.badRequest().body("Cada item debe tener utensilId y cantidad > 0");
                }

                Optional<Utensil> utensil = utensilRepository.findById(itemReq.getUtensilId());
                if (!utensil.isPresent()) {
                    return ResponseEntity.badRequest().body("Utensil con id " + itemReq.getUtensilId() + " no existe");
                }

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setUtensil(utensil.get());
                item.setQuantity(itemReq.getQuantity());
                item.setUnitPrice(utensil.get().getPrice());

                order.addOrderItem(item);
            }

            UtensilOrder savedOrder = utensilOrderRepository.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la orden: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/markArrived")
    public ResponseEntity<?> markOrderArrived(@PathVariable("id") Long orderId) {
        try {
            Optional<UtensilOrder> existingOrder = utensilOrderRepository.findById(orderId);
            if (!existingOrder.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            UtensilOrder order = existingOrder.get();
            order.setArrived(true);

            // For each OrderItem, add quantity to StockStorage of the linked storage
            Storage storage = order.getStorage();
            
            for (OrderItem item : order.getOrderItems()) {
                Optional<StockStorage> existingStockStorage = stockStorageRepository.findByUtensilIdAndStorageId(
                        item.getUtensil().getId(),
                        storage.getId()
                );

                StockStorage stockStorage;
                if (existingStockStorage.isPresent()) {
                    stockStorage = existingStockStorage.get();
                    stockStorage.setQuantity(stockStorage.getQuantity() + item.getQuantity());
                } else {
                    stockStorage = new StockStorage();
                    stockStorage.setUtensil(item.getUtensil());
                    stockStorage.setStorage(storage);
                    stockStorage.setQuantity(item.getQuantity());
                }

                stockStorageRepository.save(stockStorage);
            }

            UtensilOrder updatedOrder = utensilOrderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al marcar orden como llegada: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long orderId) {
        try {
            Optional<UtensilOrder> order = utensilOrderRepository.findById(orderId);
            if (!order.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            utensilOrderRepository.deleteById(orderId);
            return ResponseEntity.ok("Orden eliminada correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la orden: " + e.getMessage());
        }
    }
}
