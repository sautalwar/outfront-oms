package com.outfront.oms.repository;

import com.outfront.oms.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * Data access layer for inventory items.
 */
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {

    Optional<InventoryItem> findBySku(String sku);

    List<InventoryItem> findByNameContainingIgnoreCase(String name);

    List<InventoryItem> findByLocationContainingIgnoreCase(String location);

    List<InventoryItem> findByCategory(String category);

    @Query("SELECT i FROM InventoryItem i WHERE i.quantity <= i.reorderLevel")
    List<InventoryItem> findLowStockItems();

    @Query("SELECT DISTINCT i.category FROM InventoryItem i ORDER BY i.category")
    List<String> findAllCategories();

    @Query("SELECT i FROM InventoryItem i WHERE i.name LIKE %:query% OR i.sku LIKE %:query% OR i.location LIKE %:query%")
    List<InventoryItem> search(String query);
}
