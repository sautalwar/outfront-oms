package com.outfront.oms.controller;

import com.outfront.oms.model.Order;
import com.outfront.oms.model.Order.OrderStatus;
import com.outfront.oms.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldReturnAllOrders_whenGetOrders() throws Exception {
        Order order = new Order("Clear Channel", "LED Panel", 5, LocalDate.now());
        order.setId(1L);
        when(orderService.getOrders(null, null)).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("Clear Channel"))
                .andExpect(jsonPath("$[0].productName").value("LED Panel"));
    }

    @Test
    void shouldReturnOrder_whenGetOrderById() throws Exception {
        Order order = new Order("JCDecaux", "Digital Screen", 3, LocalDate.now());
        order.setId(1L);
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("JCDecaux"));
    }

    @Test
    void shouldCreateOrder_whenValidRequest() throws Exception {
        Order order = new Order("Lamar", "Transit Display", 6, LocalDate.now());
        order.setId(1L);
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "customerName": "Lamar",
                                "productName": "Transit Display",
                                "quantity": 6,
                                "orderDate": "2024-11-01"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName").value("Lamar"));
    }

    @Test
    void shouldUpdateOrderStatus_whenValidTransition() throws Exception {
        Order order = new Order("Adams", "Solar Controller", 8, LocalDate.now());
        order.setId(1L);
        order.setStatus(OrderStatus.CONFIRMED);
        when(orderService.updateOrderStatus(1L, OrderStatus.CONFIRMED)).thenReturn(order);

        mockMvc.perform(put("/api/orders/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"status": "CONFIRMED"}
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void shouldReturnOrderSummary() throws Exception {
        var summary = new OrderService.OrderSummary(10, 3, 4, 2, 1, 0);
        when(orderService.getOrderSummary()).thenReturn(summary);

        mockMvc.perform(get("/api/orders/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(10))
                .andExpect(jsonPath("$.pending").value(3));
    }
}
