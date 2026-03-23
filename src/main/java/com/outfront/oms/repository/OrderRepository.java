package com.outfront.oms.repository;

import com.outfront.oms.model.Order;
import com.outfront.oms.model.Order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

/**
 * Data access layer for customer orders.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerNameContainingIgnoreCase(String customerName);

    List<Order> findByProductNameContainingIgnoreCase(String productName);

    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.customerName LIKE %:customer%")
    List<Order> findByStatusAndCustomer(@Param("status") OrderStatus status,
                                        @Param("customer") String customer);

    long countByStatus(OrderStatus status);
}
