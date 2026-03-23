package com.outfront.oms.controller;

import com.outfront.oms.model.PurchaseOrder;
import com.outfront.oms.model.PurchaseOrder.PurchaseOrderStatus;
import com.outfront.oms.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing purchase orders to suppliers.
 * Base path: /api/purchase-orders
 */
@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    /** GET /api/purchase-orders — List all purchase orders. */
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrders());
    }

    /** GET /api/purchase-orders/{id} — Get a single purchase order. */
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderById(id));
    }

    /** GET /api/purchase-orders/status/{status} — Filter by status. */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseOrder>> getByStatus(@PathVariable PurchaseOrderStatus status) {
        return ResponseEntity.ok(purchaseOrderService.getByStatus(status));
    }

    /** GET /api/purchase-orders/supplier/{supplierId} — Filter by supplier. */
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<PurchaseOrder>> getBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(purchaseOrderService.getBySupplier(supplierId));
    }

    /** POST /api/purchase-orders — Create a new purchase order. */
    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@Valid @RequestBody PurchaseOrder po) {
        PurchaseOrder created = purchaseOrderService.createPurchaseOrder(po);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/purchase-orders/{id}/status — Update purchase order status. */
    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        PurchaseOrderStatus newStatus = PurchaseOrderStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(purchaseOrderService.updateStatus(id, newStatus));
    }
}
