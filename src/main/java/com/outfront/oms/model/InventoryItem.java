package com.outfront.oms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

/**
 * Represents a physical inventory item in the OutFront warehouse system.
 * Tracks billboard components, digital displays, cables, and accessories
 * across multiple warehouse locations.
 */
@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "SKU is required")
    @Column(nullable = false, unique = true)
    private String sku;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    private String description;

    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category;

    @PositiveOrZero(message = "Quantity cannot be negative")
    @Column(nullable = false)
    private int quantity;

    @PositiveOrZero(message = "Reorder level cannot be negative")
    @Column(nullable = false)
    private int reorderLevel = 10;

    private double unitPrice;

    private String location;

    @Column(nullable = false)
    private LocalDate lastUpdated;

    public InventoryItem() {
        this.lastUpdated = LocalDate.now();
    }

    public InventoryItem(String sku, String name, String description, String category,
                         int quantity, int reorderLevel, double unitPrice, String location) {
        this();
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.unitPrice = unitPrice;
        this.location = location;
    }

    /**
     * Checks whether the current stock level is at or below the reorder threshold.
     */
    public boolean isLowStock() {
        return this.quantity <= this.reorderLevel;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }
}
