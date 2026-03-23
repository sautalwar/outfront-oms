package com.outfront.oms.service;

import com.outfront.oms.exception.ResourceNotFoundException;
import com.outfront.oms.model.InventoryItem;
import com.outfront.oms.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Business logic for managing warehouse inventory.
 * Handles stock queries, updates, and low-stock alerts.
 */
@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /** Retrieves all inventory items. */
    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    /** Retrieves a single item by ID. */
    public InventoryItem getItemById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", id));
    }

    /** Retrieves a single item by SKU. */
    public InventoryItem getItemBySku(String sku) {
        return inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem not found with SKU: " + sku));
    }

    /** Searches items by name, SKU, or location. */
    public List<InventoryItem> search(String query) {
        return inventoryRepository.search(query);
    }

    /** Returns items by category. */
    public List<InventoryItem> getByCategory(String category) {
        return inventoryRepository.findByCategory(category);
    }

    /** Returns all distinct categories. */
    public List<String> getAllCategories() {
        return inventoryRepository.findAllCategories();
    }

    /** Returns all items at or below their reorder level. */
    public List<InventoryItem> getLowStockItems() {
        return inventoryRepository.findLowStockItems();
    }

    /** Creates a new inventory item. */
    @Transactional
    public InventoryItem createItem(InventoryItem item) {
        item.setLastUpdated(LocalDate.now());
        InventoryItem saved = inventoryRepository.save(item);
        log.info("Inventory item created: id={}, sku={}, name={}", saved.getId(), saved.getSku(), saved.getName());
        return saved;
    }

    /** Updates an existing inventory item. */
    @Transactional
    public InventoryItem updateItem(Long id, InventoryItem updates) {
        InventoryItem existing = getItemById(id);
        existing.setName(updates.getName());
        existing.setDescription(updates.getDescription());
        existing.setCategory(updates.getCategory());
        existing.setQuantity(updates.getQuantity());
        existing.setReorderLevel(updates.getReorderLevel());
        existing.setUnitPrice(updates.getUnitPrice());
        existing.setLocation(updates.getLocation());
        existing.setLastUpdated(LocalDate.now());
        InventoryItem saved = inventoryRepository.save(existing);
        log.info("Inventory item updated: id={}, sku={}", saved.getId(), saved.getSku());
        return saved;
    }

    /** Adjusts stock quantity (positive = add, negative = remove). */
    @Transactional
    public InventoryItem adjustStock(Long id, int adjustment) {
        InventoryItem item = getItemById(id);
        int newQuantity = item.getQuantity() + adjustment;
        if (newQuantity < 0) {
            throw new IllegalStateException(
                    "Stock adjustment would result in negative quantity for " + item.getSku());
        }
        item.setQuantity(newQuantity);
        item.setLastUpdated(LocalDate.now());
        InventoryItem saved = inventoryRepository.save(item);
        log.info("Stock adjusted: sku={}, adjustment={}, newQty={}", item.getSku(), adjustment, newQuantity);
        return saved;
    }

    /** Deletes an inventory item by ID. */
    @Transactional
    public void deleteItem(Long id) {
        InventoryItem item = getItemById(id);
        inventoryRepository.delete(item);
        log.info("Inventory item deleted: id={}, sku={}", id, item.getSku());
    }
}
