package com.outfront.oms.exception;

/**
 * Thrown when an order cannot be confirmed due to insufficient inventory.
 */
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String productName, int requested, int available) {
        super("Insufficient stock for '" + productName + "': requested " + requested + ", available " + available);
    }
}
