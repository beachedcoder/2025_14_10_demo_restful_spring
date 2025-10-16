package com.training.homework.whoms.controller;

import com.training.homework.whoms.dto.Warehouse;
import com.training.homework.whoms.service.WarehouseDtoData;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller for basic demo purposes.
 */
@RestController
@RequestMapping(value = "/whoms/v1/", produces = "application/json", consumes = "application/json")
public class DemoRestController {

    private static final Logger logger = LoggerFactory.getLogger(DemoRestController.class);
    
    private final WarehouseDtoData warehouseDtoData;

    public DemoRestController(final WarehouseDtoData warehouseDtoData) {
        this.warehouseDtoData = warehouseDtoData;
    }

    /**
     * Returns all warehouses with HTTP 200 OK status.
     *
     * @return ResponseEntity containing list of warehouses with 200 OK status
     */
    @GetMapping("warehouses")
    public ResponseEntity<List<Warehouse>> getWarehouses() {
        logger.debug("Received request for /warehouses endpoint");
        
        final List<Warehouse> warehouses = warehouseDtoData.getAllWarehouses();
        
        logger.info("Retrieved {} warehouses for /warehouses endpoint", warehouses.size());
        
        return ResponseEntity.ok(warehouses);
    }

    /**
     * Requests warehouse space for a given warehouse.
     *
     * @param warehouse the warehouse DTO containing space request details
     * @return ResponseEntity with accepted status if request is processed successfully
     */
    @PostMapping("warehouse/request-space")
    public ResponseEntity<Void> requestWarehouseSpace(@RequestBody final Warehouse warehouse) {
        logger.debug("Received request for warehouse space: {}", warehouse);
        
        // Process the warehouse space request
        // TODO: Additional validation and business logic would go here
        
        logger.info("Successfully processed warehouse space request for warehouse: {}", warehouse.name());
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}