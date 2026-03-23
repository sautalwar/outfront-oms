package com.outfront.oms.service;

import com.outfront.oms.exception.InsufficientStockException;
import com.outfront.oms.exception.ResourceNotFoundException;
import com.outfront.oms.model.InventoryItem;
import com.outfront.oms.model.Order;
import com.outfront.oms.model.Order.OrderStatus;
import com.outfront.oms.repository.InventoryRepository;
import com.outfront.oms.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrder_withPendingStatus() {
        Order order = new Order("Clear Channel", "LED Panel", 5, LocalDate.now());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertEquals(OrderStatus.PENDING, result.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void shouldThrowException_whenOrderNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(999L));
    }

    @Test
    void shouldConfirmOrder_whenSufficientStock() {
        Order order = new Order("JCDecaux", "LED Panel", 5, LocalDate.now());
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);

        InventoryItem item = new InventoryItem("LED-7236", "LED Panel", "desc", "Displays", 50, 10, 2800.0, "WH-A");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(inventoryRepository.findByNameContainingIgnoreCase("LED Panel")).thenReturn(List.of(item));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(inventoryRepository.save(any(InventoryItem.class))).thenReturn(item);

        Order result = orderService.updateOrderStatus(1L, OrderStatus.CONFIRMED);

        assertEquals(OrderStatus.CONFIRMED, result.getStatus());
        assertEquals(45, item.getQuantity());
    }

    @Test
    void shouldThrowException_whenInsufficientStock() {
        Order order = new Order("Lamar", "LED Panel", 100, LocalDate.now());
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);

        InventoryItem item = new InventoryItem("LED-7236", "LED Panel", "desc", "Displays", 50, 10, 2800.0, "WH-A");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(inventoryRepository.findByNameContainingIgnoreCase("LED Panel")).thenReturn(List.of(item));

        assertThrows(InsufficientStockException.class,
                () -> orderService.updateOrderStatus(1L, OrderStatus.CONFIRMED));
    }

    @Test
    void shouldThrowException_whenInvalidStatusTransition() {
        Order order = new Order("Adams", "Solar Controller", 8, LocalDate.now());
        order.setId(1L);
        order.setStatus(OrderStatus.DELIVERED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class,
                () -> orderService.updateOrderStatus(1L, OrderStatus.PENDING));
    }

    @Test
    void shouldRestoreInventory_whenCancellingConfirmedOrder() {
        Order order = new Order("JCDecaux", "LED Panel", 5, LocalDate.now());
        order.setId(1L);
        order.setStatus(OrderStatus.CONFIRMED);

        InventoryItem item = new InventoryItem("LED-7236", "LED Panel", "desc", "Displays", 45, 10, 2800.0, "WH-A");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(inventoryRepository.findByNameContainingIgnoreCase("LED Panel")).thenReturn(List.of(item));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(inventoryRepository.save(any(InventoryItem.class))).thenReturn(item);

        orderService.updateOrderStatus(1L, OrderStatus.CANCELLED);

        assertEquals(50, item.getQuantity());
    }

    @Test
    void shouldNotDeleteOrder_whenStatusIsShipped() {
        Order order = new Order("Lamar", "Transit Display", 6, LocalDate.now());
        order.setId(1L);
        order.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () -> orderService.deleteOrder(1L));
    }
}
