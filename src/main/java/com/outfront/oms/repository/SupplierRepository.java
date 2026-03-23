package com.outfront.oms.repository;

import com.outfront.oms.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Data access layer for suppliers.
 */
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByActive(boolean active);

    List<Supplier> findByNameContainingIgnoreCase(String name);
}
