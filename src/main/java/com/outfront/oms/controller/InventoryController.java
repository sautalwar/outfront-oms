package com.outfront.oms.controller;

import com.outfront.oms.model.InventoryItem;
import com.outfront.oms.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing warehouse inventory.
 * Base path: /api/inventory
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /** GET /api/inventory — List all inventory items. */
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    /** GET /api/inventory/{id} — Get a single item by ID. */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getItemById(id));
    }

    /** GET /api/inventory/sku/{sku} — Get a single item by SKU. */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<InventoryItem> getItemBySku(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.getItemBySku(sku));
    }

    /** GET /api/inventory/search?q=query — Search items by name, SKU, or location. */
    @GetMapping("/search")
    public ResponseEntity<List<InventoryItem>> search(@RequestParam String q) {
        return ResponseEntity.ok(inventoryService.search(q));
    }

    /** GET /api/inventory/categories — List all distinct categories. */
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(inventoryService.getAllCategories());
    }

    /** GET /api/inventory/category/{category} — List items in a category. */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<InventoryItem>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(inventoryService.getByCategory(category));
    }

    /** GET /api/inventory/low-stock — List items at or below reorder level. */
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryItem>> getLowStockItems() {
        return ResponseEntity.ok(inventoryService.getLowStockItems());
    }

    /** POST /api/inventory — Create a new inventory item. */
    @PostMapping
    public ResponseEntity<InventoryItem> createItem(@Valid @RequestBody InventoryItem item) {
        InventoryItem created = inventoryService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/inventory/{id} — Update an existing inventory item. */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Long id,
                                                     @Valid @RequestBody InventoryItem item) {
        return ResponseEntity.ok(inventoryService.updateItem(id, item));
    }

    /** PATCH /api/inventory/{id}/stock — Adjust stock quantity (+ or -). */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<InventoryItem> adjustStock(@PathVariable Long id,
                                                      @RequestBody Map<String, Integer> body) {
        int adjustment = body.get("adjustment");
        return ResponseEntity.ok(inventoryService.adjustStock(id, adjustment));
    }

    /** DELETE /api/inventory/{id} — Delete an inventory item. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        inventoryService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
