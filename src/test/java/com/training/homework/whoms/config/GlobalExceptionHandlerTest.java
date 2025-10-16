package com.training.homework.whoms.config;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.homework.whoms.controller.DemoRestController;
import com.training.homework.whoms.dto.Warehouse;
import com.training.homework.whoms.service.WarehouseDtoData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for GlobalExceptionHandler integration with DemoRestController.
 */
@WebMvcTest(DemoRestController.class)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WarehouseDtoData warehouseDtoData;

    @Test
    void shouldReturnStandardErrorResponse_whenRuntimeExceptionThrownFromController() throws Exception {
        // Given
        when(warehouseDtoData.getAllWarehouses()).thenThrow(new RuntimeException("Database connection failed"));

        // When & Then
        mockMvc.perform(get("/whoms/v1/warehouses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Our apologies for not being able to service your request at present"));
    }

    @Test
    void shouldReturnStandardErrorResponse_whenInvalidJsonSentToPostEndpoint() throws Exception {
        // Given
        final String invalidJson = "{ invalid json structure }";

        // When & Then
        mockMvc.perform(post("/whoms/v1/warehouse/request-space")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Our apologies for not being able to service your request at present"));
    }

    @Test
    void shouldReturnStandardErrorResponse_whenUnsupportedMediaTypeSent() throws Exception {
        // Given
        final var warehouse = new Warehouse("Test Warehouse", "789 Test St", "Test City", "TS", "12345", "(123) 456-7890", 5000, 10);
        final String warehouseJson = objectMapper.writeValueAsString(warehouse);

        // When & Then
        mockMvc.perform(post("/whoms/v1/warehouse/request-space")
                .contentType(MediaType.APPLICATION_XML)
                .content(warehouseJson))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Our apologies for not being able to service your request at present"));
    }

    @Test
    void shouldReturnStandardErrorResponse_whenMissingContentTypeOnPostRequest() throws Exception {
        // Given
        final var warehouse = new Warehouse("Test Warehouse", "789 Test St", "Test City", "TS", "12345", "(123) 456-7890", 5000, 10);
        final String warehouseJson = objectMapper.writeValueAsString(warehouse);

        // When & Then
        mockMvc.perform(post("/whoms/v1/warehouse/request-space")
                .content(warehouseJson))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Our apologies for not being able to service your request at present"));
    }
}