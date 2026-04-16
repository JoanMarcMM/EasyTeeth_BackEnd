package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    private OrderItemRepository orderItemRepository;
    private UtensilOrderRepository utensilOrderRepository;
    private UtensilRepository utensilRepository;

    public OrderItemController(
            OrderItemRepository orderItemRepository,
            UtensilOrderRepository utensilOrderRepository,
            UtensilRepository utensilRepository
    ) {
        this.orderItemRepository = orderItemRepository;
        this.utensilOrderRepository = utensilOrderRepository;
        this.utensilRepository = utensilRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderItem>> getOrderItem(@PathVariable("id") Long itemId)
            throws IOException {
        Optional<OrderItem> item = orderItemRepository.findById(itemId);
        if (item.isPresent()) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getItemsByOrder(@PathVariable("orderId") Long orderId)
            throws IOException {
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/index")
    public ResponseEntity<List<OrderItem>> getAll() throws IOException {
        List<OrderItem> items = orderItemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createOrderItem(@RequestBody OrderItemRequest req) {
        try {
            if (req == null || req.getOrderId() == null || req.getUtensilId() == null || 
                req.getQuantity() == null || req.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("Faltan campos obligatorios o cantidad no válida");
            }

            Optional<UtensilOrder> order = utensilOrderRepository.findById(req.getOrderId());
            if (!order.isPresent()) {
                return ResponseEntity.badRequest().body("Orden con id " + req.getOrderId() + " no existe");
            }

            Optional<Utensil> utensil = utensilRepository.findById(req.getUtensilId());
            if (!utensil.isPresent()) {
                return ResponseEntity.badRequest().body("Utensil con id " + req.getUtensilId() + " no existe");
            }

            OrderItem item = new OrderItem();
            item.setOrder(order.get());
            item.setUtensil(utensil.get());
            item.setQuantity(req.getQuantity());
            item.setUnitPrice(req.getUnitPrice() != null ? req.getUnitPrice() : utensil.get().getPrice());

            OrderItem savedItem = orderItemRepository.save(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el item de la orden: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderItem(@PathVariable("id") Long itemId, @RequestBody OrderItemRequest req) {
        try {
            Optional<OrderItem> existingItem = orderItemRepository.findById(itemId);
            if (!existingItem.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            OrderItem item = existingItem.get();

            if (req.getQuantity() != null && req.getQuantity() > 0) {
                item.setQuantity(req.getQuantity());
            }

            if (req.getUnitPrice() != null && req.getUnitPrice() > 0) {
                item.setUnitPrice(req.getUnitPrice());
            }

            OrderItem updatedItem = orderItemRepository.save(item);
            return ResponseEntity.ok(updatedItem);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el item de la orden: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable("id") Long itemId) {
        try {
            Optional<OrderItem> item = orderItemRepository.findById(itemId);
            if (!item.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            orderItemRepository.deleteById(itemId);
            return ResponseEntity.ok("Item de la orden eliminado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el item de la orden: " + e.getMessage());
        }
    }
}
