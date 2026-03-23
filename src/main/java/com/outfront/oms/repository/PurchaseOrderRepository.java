package com.outfront.oms.repository;

import com.outfront.oms.model.PurchaseOrder;
import com.outfront.oms.model.PurchaseOrder.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Data access layer for purchase orders.
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findByStatus(PurchaseOrderStatus status);

    List<PurchaseOrder> findBySupplierId(Long supplierId);

    List<PurchaseOrder> findByInventoryItemId(Long inventoryItemId);
}
