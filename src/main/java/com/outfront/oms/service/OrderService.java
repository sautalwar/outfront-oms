package com.outfront.oms.service;

import com.outfront.oms.exception.InsufficientStockException;
import com.outfront.oms.exception.ResourceNotFoundException;
import com.outfront.oms.model.InventoryItem;
import com.outfront.oms.model.Order;
import com.outfront.oms.model.Order.OrderStatus;
import com.outfront.oms.repository.InventoryRepository;
import com.outfront.oms.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Business logic for managing customer orders.
 * Handles order creation, status transitions, and inventory validation.
 */
@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;

    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Retrieves all orders, optionally filtered by status and/or customer name.
     */
    public List<Order> getOrders(OrderStatus status, String customer) {
        if (status != null && customer != null) {
            return orderRepository.findByStatusAndCustomer(status, customer);
        } else if (status != null) {
            return orderRepository.findByStatus(status);
        } else if (customer != null) {
            return orderRepository.findByCustomerNameContainingIgnoreCase(customer);
        }
        return orderRepository.findAll();
    }

    /**
     * Retrieves a single order by ID or throws ResourceNotFoundException.
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }

    /**
     * Retrieves orders within a date range.
     */
    public List<Order> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    /**
     * Creates a new order with PENDING status and today's date.
     */
    @Transactional
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        Order saved = orderRepository.save(order);
        log.info("Order created: id={}, customer={}, product={}", saved.getId(),
                saved.getCustomerName(), saved.getProductName());
        return saved;
    }

    /**
     * Transitions an order to a new status.
     * When confirming, validates that sufficient inventory exists.
     * When marking as delivered, restocks cannot happen (final state).
     */
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = getOrderById(id);
        OrderStatus currentStatus = order.getStatus();

        validateStatusTransition(currentStatus, newStatus);

        if (newStatus == OrderStatus.CONFIRMED) {
            deductInventory(order.getProductName(), order.getQuantity());
        }

        if (newStatus == OrderStatus.CANCELLED && currentStatus == OrderStatus.CONFIRMED) {
            restoreInventory(order.getProductName(), order.getQuantity());
        }

        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);
        log.info("Order {} status changed: {} -> {}", id, currentStatus, newStatus);
        return updated;
    }

    /**
     * Deletes an order. Only PENDING or CANCELLED orders can be deleted.
     */
    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cannot delete order in " + order.getStatus() + " status. Cancel it first.");
        }
        orderRepository.delete(order);
        log.info("Order deleted: id={}", id);
    }

    /**
     * Returns a summary of order counts grouped by status.
     */
    public OrderSummary getOrderSummary() {
        return new OrderSummary(
                orderRepository.count(),
                orderRepository.countByStatus(OrderStatus.PENDING),
                orderRepository.countByStatus(OrderStatus.CONFIRMED),
                orderRepository.countByStatus(OrderStatus.SHIPPED),
                orderRepository.countByStatus(OrderStatus.DELIVERED),
                orderRepository.countByStatus(OrderStatus.CANCELLED)
        );
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus next) {
        boolean valid = switch (current) {
            case PENDING -> next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELLED;
            case CONFIRMED -> next == OrderStatus.SHIPPED || next == OrderStatus.CANCELLED;
            case SHIPPED -> next == OrderStatus.DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };
        if (!valid) {
            throw new IllegalStateException(
                    "Invalid status transition: " + current + " -> " + next);
        }
    }

    private void deductInventory(String productName, int quantity) {
        InventoryItem item = inventoryRepository.findByNameContainingIgnoreCase(productName)
                .stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No inventory item found matching product: " + productName));

        if (item.getQuantity() < quantity) {
            throw new InsufficientStockException(productName, quantity, item.getQuantity());
        }

        item.setQuantity(item.getQuantity() - quantity);
        item.setLastUpdated(LocalDate.now());
        inventoryRepository.save(item);
        log.info("Inventory deducted: {} x {} (remaining: {})", quantity, productName, item.getQuantity());
    }

    private void restoreInventory(String productName, int quantity) {
        inventoryRepository.findByNameContainingIgnoreCase(productName)
                .stream().findFirst()
                .ifPresent(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    item.setLastUpdated(LocalDate.now());
                    inventoryRepository.save(item);
                    log.info("Inventory restored: {} x {} (new total: {})", quantity, productName, item.getQuantity());
                });
    }

    /**
     * Summary record for order dashboard statistics.
     */
    public record OrderSummary(long total, long pending, long confirmed,
                                long shipped, long delivered, long cancelled) {}
}
