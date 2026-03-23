package com.outfront.oms.service;

import com.outfront.oms.exception.ResourceNotFoundException;
import com.outfront.oms.model.InventoryItem;
import com.outfront.oms.model.PurchaseOrder;
import com.outfront.oms.model.PurchaseOrder.PurchaseOrderStatus;
import com.outfront.oms.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Business logic for managing purchase orders to suppliers.
 * Handles the full lifecycle from DRAFT → SUBMITTED → APPROVED → SHIPPED → RECEIVED.
 */
@Service
public class PurchaseOrderService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderService.class);

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final InventoryService inventoryService;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                 InventoryService inventoryService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.inventoryService = inventoryService;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PurchaseOrder", id));
    }

    public List<PurchaseOrder> getByStatus(PurchaseOrderStatus status) {
        return purchaseOrderRepository.findByStatus(status);
    }

    public List<PurchaseOrder> getBySupplier(Long supplierId) {
        return purchaseOrderRepository.findBySupplierId(supplierId);
    }

    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
        po.setStatus(PurchaseOrderStatus.DRAFT);
        po.setOrderDate(LocalDate.now());
        PurchaseOrder saved = purchaseOrderRepository.save(po);
        log.info("Purchase order created: id={}, supplier={}, item={}",
                saved.getId(), saved.getSupplier().getName(), saved.getInventoryItem().getSku());
        return saved;
    }

    /**
     * Transitions a purchase order to a new status.
     * When RECEIVED, automatically restocks the inventory item.
     */
    @Transactional
    public PurchaseOrder updateStatus(Long id, PurchaseOrderStatus newStatus) {
        PurchaseOrder po = getPurchaseOrderById(id);
        PurchaseOrderStatus current = po.getStatus();

        validateTransition(current, newStatus);

        po.setStatus(newStatus);

        if (newStatus == PurchaseOrderStatus.RECEIVED) {
            InventoryItem item = po.getInventoryItem();
            inventoryService.adjustStock(item.getId(), po.getQuantityOrdered());
            log.info("Inventory restocked from PO {}: {} x {}", id,
                    po.getQuantityOrdered(), item.getSku());
        }

        PurchaseOrder saved = purchaseOrderRepository.save(po);
        log.info("Purchase order {} status changed: {} -> {}", id, current, newStatus);
        return saved;
    }

    private void validateTransition(PurchaseOrderStatus current, PurchaseOrderStatus next) {
        boolean valid = switch (current) {
            case DRAFT -> next == PurchaseOrderStatus.SUBMITTED || next == PurchaseOrderStatus.CANCELLED;
            case SUBMITTED -> next == PurchaseOrderStatus.APPROVED || next == PurchaseOrderStatus.CANCELLED;
            case APPROVED -> next == PurchaseOrderStatus.SHIPPED || next == PurchaseOrderStatus.CANCELLED;
            case SHIPPED -> next == PurchaseOrderStatus.RECEIVED;
            case RECEIVED, CANCELLED -> false;
        };
        if (!valid) {
            throw new IllegalStateException(
                    "Invalid purchase order transition: " + current + " -> " + next);
        }
    }
}
