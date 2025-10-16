package com.training.homework.whoms.dto;

/**
 * Warehouse data transfer object representing a warehouse entity.
 *
 * @param name warehouse name
 * @param address street address of the warehouse
 * @param city city where warehouse is located
 * @param state state or province where warehouse is located
 * @param postalCode postal or ZIP code of the warehouse
 * @param warehousePhone contact phone number for the warehouse
 * @param squareFootage total square footage of the warehouse
 * @param loadingDocks number of loading docks available
 */
public record Warehouse(
        String name,
        String address,
        String city,
        String state,
        String postalCode,
        String warehousePhone,
        int squareFootage,
        int loadingDocks
) {       
}