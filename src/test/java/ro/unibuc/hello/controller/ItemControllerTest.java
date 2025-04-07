package ro.unibuc.hello.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ItemService;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private Item sampleItem;

    @BeforeEach
    void setUp() {
        sampleItem = new Item("1", "Test Item", "Description", 100.0, LocalDateTime.now().plusDays(1), true, "test@example.com", null);
    }

    @Test
    void getAllItems_ShouldReturnList() {
        when(itemService.getAllItems()).thenReturn(Collections.singletonList(sampleItem));
        ResponseEntity<List<Item>> response = itemController.getAllItems(false);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getItemById_ShouldReturnItem() {
        when(itemService.getItemById("1")).thenReturn(sampleItem);
        ResponseEntity<Item> response = itemController.getItemById("1");
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getItemById_ShouldReturnNotFound() {
        when(itemService.getItemById("99")).thenThrow(new EntityNotFoundException("99"));
        ResponseEntity<Item> response = itemController.getItemById("99");
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void createItem_ShouldReturnCreated() {
        when(itemService.createItem(any(Item.class))).thenReturn(sampleItem);
        ResponseEntity<Item> response = itemController.createItem(sampleItem);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void createItem_ShouldReturnBadRequestForInvalidEmail() {
        when(itemService.createItem(any(Item.class))).thenThrow(new IllegalArgumentException("Invalid email format"));
        ResponseEntity<Item> response = itemController.createItem(sampleItem);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void deleteItem_ShouldReturnNoContent() {
        doNothing().when(itemService).deleteItem("1");
        ResponseEntity<Void> response = itemController.deleteItem("1");
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void deleteItem_ShouldReturnNotFound() {
        doThrow(new EntityNotFoundException("99")).when(itemService).deleteItem("99");
        ResponseEntity<Void> response = itemController.deleteItem("99");
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void getAllItems_ActiveOnly_ShouldReturnList() {
        when(itemService.getActiveItems()).thenReturn(Collections.singletonList(sampleItem));
        ResponseEntity<List<Item>> response = itemController.getAllItems(true);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void searchItemByName_ShouldReturnItem() {
        when(itemService.searchItemByName("Test Item")).thenReturn(sampleItem);
        ResponseEntity<Item> response = itemController.searchItemByName("Test Item");
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void searchItemByName_ShouldReturnNotFound() {
        when(itemService.searchItemByName("Unknown")).thenThrow(new EntityNotFoundException("Unknown"));
        ResponseEntity<Item> response = itemController.searchItemByName("Unknown");
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void updateItem_ShouldReturnUpdatedItem() {
        when(itemService.updateItem("1", sampleItem)).thenReturn(sampleItem);
        ResponseEntity<Item> response = itemController.updateItem("1", sampleItem);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void updateItem_ShouldReturnNotFound() {
        when(itemService.updateItem("99", sampleItem)).thenThrow(new EntityNotFoundException("99"));
        ResponseEntity<Item> response = itemController.updateItem("99", sampleItem);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void updateItem_ShouldReturnBadRequest() {
        when(itemService.updateItem("1", sampleItem)).thenThrow(new IllegalArgumentException("Invalid data"));
        ResponseEntity<Item> response = itemController.updateItem("1", sampleItem);
        assertEquals(400, response.getStatusCode().value());
    }

}
