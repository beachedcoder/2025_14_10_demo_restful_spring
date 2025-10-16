package com.training.homework.whoms.service;

import com.training.homework.whoms.dto.Warehouse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service providing static warehouse data for testing and demonstration purposes.
 * 
 * <p>This service contains a predefined set of warehouse records that can be used
 * for development, testing, and demonstration of warehouse management functionality.
 */
@Service
public class WarehouseDtoDataImpl implements WarehouseDtoData {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseDtoDataImpl.class);

    private static final List<Warehouse> WAREHOUSES = List.of(
        new Warehouse(
            "Providence Distribution Center",
            "1234 Industrial Way",
            "Providence",
            "RI",
            "02903",
            "(401) 555-0101",
            1500000,
            15
        ),
        new Warehouse(
            "Albany Regional Warehouse",
            "5678 Commerce Blvd",
            "Albany",
            "NY",
            "12205",
            "(518) 555-0201",
            600000,
            36
        ),
        new Warehouse(
            "Newark Logistics Hub",
            "9012 Freight Avenue",
            "Newark",
            "NJ",
            "07102",
            "(973) 555-0301",
            250000,
            24
        ),
        new Warehouse(
            "Philadelphia Storage Facility",
            "3456 Warehouse Drive",
            "Philadelphia",
            "PA",
            "19104",
            "(215) 555-0401",
            180000,
            15
        ),
        new Warehouse(
            "Richmond Distribution Point",
            "7890 Supply Chain Road",
            "Richmond",
            "VA",
            "23230",
            "(804) 555-0501",
            175000,
            14
        ),
        new Warehouse(
            "Charlotte Fulfillment Center",
            "2468 Logistics Lane",
            "Charlotte",
            "NC",
            "28202",
            "(704) 555-0601",
            3000000,
            30
        ),
        new Warehouse(
            "Jacksonville Coastal Warehouse",
            "1357 Port Access Road",
            "Jacksonville",
            "FL",
            "32202",
            "(904) 555-0701",
            280000,
            20
        ),
        new Warehouse(
            "Houston Energy District Depot",
            "8642 Petroleum Plaza",
            "Houston",
            "TX",
            "77002",
            "(713) 555-0801",
            550000,
            35
        ),
        new Warehouse(
            "Sioux Falls Agricultural Center",
            "9753 Prairie Commerce Way",
            "Sioux Falls",
            "SD",
            "57104",
            "(605) 555-0901",
            120000,
            10
        )
    );

    /**
     * Returns a copy of all warehouse data.
     *
     * @return immutable copy of the warehouse list
     */
    public List<Warehouse> getAllWarehouses() {
        logger.debug("Retrieving all warehouse data");
        
        final List<Warehouse> warehouseCopy = List.copyOf(WAREHOUSES);
        
        logger.info("Retrieved {} warehouses", warehouseCopy.size());
        return warehouseCopy;
    }

    /**
     * Returns the total count of warehouses.
     *
     * @return number of warehouses in the data set
     */
    public int getWarehouseCount() {
        final int count = WAREHOUSES.size();
        logger.debug("Warehouse count: {}", count);
        return count;
    }
}