package com.outfront.oms.service;

import com.outfront.oms.exception.ResourceNotFoundException;
import com.outfront.oms.model.Supplier;
import com.outfront.oms.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Business logic for managing suppliers.
 */
@Service
public class SupplierService {

    private static final Logger log = LoggerFactory.getLogger(SupplierService.class);

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Supplier> getActiveSuppliers() {
        return supplierRepository.findByActive(true);
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    }

    @Transactional
    public Supplier createSupplier(Supplier supplier) {
        Supplier saved = supplierRepository.save(supplier);
        log.info("Supplier created: id={}, name={}", saved.getId(), saved.getName());
        return saved;
    }

    @Transactional
    public Supplier updateSupplier(Long id, Supplier updates) {
        Supplier existing = getSupplierById(id);
        existing.setName(updates.getName());
        existing.setContactEmail(updates.getContactEmail());
        existing.setContactPhone(updates.getContactPhone());
        existing.setAddress(updates.getAddress());
        existing.setActive(updates.isActive());
        Supplier saved = supplierRepository.save(existing);
        log.info("Supplier updated: id={}, name={}", saved.getId(), saved.getName());
        return saved;
    }

    @Transactional
    public void deactivateSupplier(Long id) {
        Supplier supplier = getSupplierById(id);
        supplier.setActive(false);
        supplierRepository.save(supplier);
        log.info("Supplier deactivated: id={}, name={}", id, supplier.getName());
    }
}
