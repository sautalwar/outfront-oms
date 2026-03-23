package com.outfront.oms.controller;

import com.outfront.oms.model.Order;
import com.outfront.oms.model.Order.OrderStatus;
import com.outfront.oms.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing customer orders.
 * Base path: /api/orders
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /** GET /api/orders — List all orders with optional status/customer filters. */
    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String customer) {
        return ResponseEntity.ok(orderService.getOrders(status, customer));
    }

    /** GET /api/orders/{id} — Get a single order by ID. */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    /** GET /api/orders/date-range — Get orders within a date range. */
    @GetMapping("/date-range")
    public ResponseEntity<List<Order>> getOrdersByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(orderService.getOrdersByDateRange(startDate, endDate));
    }

    /** GET /api/orders/summary — Get order count summary by status. */
    @GetMapping("/summary")
    public ResponseEntity<OrderService.OrderSummary> getOrderSummary() {
        return ResponseEntity.ok(orderService.getOrderSummary());
    }

    /** POST /api/orders — Create a new order. */
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/orders/{id}/status — Update order status. */
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        OrderStatus newStatus = OrderStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(orderService.updateOrderStatus(id, newStatus));
    }

    /** DELETE /api/orders/{id} — Delete a PENDING or CANCELLED order. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
