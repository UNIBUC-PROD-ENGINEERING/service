package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.InventoryDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.InventoryService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    void testGetAllInventoryItems() throws Exception {
        List<InventoryDTO> inventoryItems = Arrays.asList(
                new InventoryDTO("1", "Item 1", 100, 10),
                new InventoryDTO("2", "Item 2", 50, 5)
        );
        when(inventoryService.getAllInventoryItems()).thenReturn(inventoryItems);

        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Item 1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Item 2"));
    }

    @Test
    void testGetInventoryItemById_ExistingEntity() throws Exception {
        String id = "1";
        InventoryDTO inventoryItem = new InventoryDTO(id, "Item 1", 100, 10);
        when(inventoryService.getInventoryItemById(id)).thenReturn(inventoryItem);

        mockMvc.perform(get("/inventory/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Item 1"));
    }

    @Test
    void testCreateInventoryItem() throws Exception {
        InventoryDTO newItem = new InventoryDTO(null, "Item 3", 200, 20);
        InventoryDTO createdItem = new InventoryDTO("3", "Item 3", 200, 20);
        when(inventoryService.createInventoryItem(any(InventoryDTO.class))).thenReturn(createdItem);

        mockMvc.perform(post("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Item 3\",\"stock\":200,\"threshold\":20}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.name").value("Item 3"));
    }

    @Test
    void testUpdateInventoryStock_ExistingEntity() throws Exception {
        String id = "1";
        Integer newStock = 150;
        InventoryDTO updatedItem = new InventoryDTO(id, "Item 1", newStock, 10);
        when(inventoryService.updateInventoryStock(eq(id), eq(newStock))).thenReturn(updatedItem);

        mockMvc.perform(put("/inventory/{id}/stock", id)
                .param("stock", newStock.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.stock").value(newStock));
    }

    @Test
    void testDeleteInventoryItem_ExistingEntity() throws Exception {
        String id = "1";
        doNothing().when(inventoryService).deleteInventoryItem(id);

        mockMvc.perform(delete("/inventory/{id}", id))
                .andExpect(status().isOk());

        verify(inventoryService, times(1)).deleteInventoryItem(id);
    }
}
