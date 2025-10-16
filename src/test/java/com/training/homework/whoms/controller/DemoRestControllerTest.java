package com.training.homework.whoms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.homework.whoms.dto.Warehouse;
import com.training.homework.whoms.service.WarehouseDtoData;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for DemoRestController.
 */
@WebMvcTest(DemoRestController.class)
@ActiveProfiles("test")
class DemoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WarehouseDtoData warehouseDtoData;

    @Test
    void shouldReturnAllWarehouses_whenGetWarehousesEndpointCalled() throws Exception {
        // Given
        final List<Warehouse> warehouses = List.of(
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
        )
        );
        
        when(warehouseDtoData.getAllWarehouses()).thenReturn(warehouses);

        // When & Then
        mockMvc.perform(get("/whoms/v1/warehouses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Providence Distribution Center"))
                .andExpect(jsonPath("$[0].address").value("1234 Industrial Way"))
                .andExpect(jsonPath("$[0].city").value("Providence"))
                .andExpect(jsonPath("$[0].state").value("RI"))
                .andExpect(jsonPath("$[0].postalCode").value("02903"))
                .andExpect(jsonPath("$[0].warehousePhone").value("(401) 555-0101"))
                .andExpect(jsonPath("$[0].squareFootage").value(1500000))
                .andExpect(jsonPath("$[0].loadingDocks").value(15))
                .andExpect(jsonPath("$[1].name").value("Albany Regional Warehouse"))
                .andExpect(jsonPath("$[1].address").value("5678 Commerce Blvd"))
                .andExpect(jsonPath("$[1].city").value("Albany"))
                .andExpect(jsonPath("$[1].state").value("NY"))
                .andExpect(jsonPath("$[1].postalCode").value("12205"))
                .andExpect(jsonPath("$[1].warehousePhone").value("(518) 555-0201"))
                .andExpect(jsonPath("$[1].squareFootage").value(600000))
                .andExpect(jsonPath("$[1].loadingDocks").value(36));
    }

    @Test
    void shouldReturnEmptyList_whenGetWarehousesEndpointCalledWithNoWarehouses() throws Exception {
        // Given
        when(warehouseDtoData.getAllWarehouses()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/whoms/v1/warehouses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnAccepted_whenRequestWarehouseSpaceWithValidWarehouse() throws Exception {
        // Given
    
        final Warehouse warehouse = new Warehouse("Test Warehouse", "789 Test St", "Test City", "TS", "12345", "(123) 456-7890", 5000, 10);
        final String warehouseJson = objectMapper.writeValueAsString(warehouse);

        // When & Then
        mockMvc.perform(post("/whoms/v1/warehouse/request-space")
                .contentType(MediaType.APPLICATION_JSON)
                .content(warehouseJson))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldReturnBadRequest_whenRequestWarehouseSpaceWithInvalidJson() throws Exception {
        // Given
        final String invalidJson = "{ invalid json }";

        // When & Then
        mockMvc.perform(post("/whoms/v1/warehouse/request-space")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnsupportedMediaType_whenRequestWarehouseSpaceWithoutContentType() throws Exception {
        // Given
        final Warehouse warehouse = new Warehouse("Test Warehouse", "789 Test St", "Test City", "TS", "12345", "(123) 456-7890", 5000, 10);
        final String warehouseJson = objectMapper.writeValueAsString(warehouse);

        // When & Then
        mockMvc.perform(post("/whoms/v1/warehouse/request-space")
                .content(warehouseJson))
                .andExpect(status().isUnsupportedMediaType());
    }
}