package com.easyteeth.EasyTeeth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "order_item",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"order_id", "utensil_id"}
    )
)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private UtensilOrder order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "utensil_id")
    private Utensil utensil;

    private int quantity;
    private double unitPrice;

    public OrderItem() {
    }

    public OrderItem(UtensilOrder order, Utensil utensil, int quantity, double unitPrice) {
        this.order = order;
        this.utensil = utensil;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UtensilOrder getOrder() {
        return order;
    }

    public void setOrder(UtensilOrder order) {
        this.order = order;
    }

    public Utensil getUtensil() {
        return utensil;
    }

    public void setUtensil(Utensil utensil) {
        this.utensil = utensil;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
