package ro.unibuc.hello.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.exception.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private ItemService itemService;

    private ItemEntity sampleItem;
    private BidEntity sampleBid;

    @BeforeEach
    void setUp() {
        sampleItem = new ItemEntity("Test Item", "Description", 100.0, LocalDateTime.now().plusDays(1), "test@example.com", null);
        sampleItem.setId("1");

        sampleBid = new BidEntity("1", "Bidder1", 150.0, "bidder@example.com");
        sampleBid.setId("101");
    }

    @Test
    void getAllItems_ShouldReturnList() {
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(sampleItem));
        List<Item> items = itemService.getAllItems();
        assertEquals(1, items.size());
        assertEquals("Test Item", items.get(0).getName());
    }

    @Test
    void getItemById_ShouldReturnItem() {
        when(itemRepository.findById("1")).thenReturn(Optional.of(sampleItem));
        when(bidRepository.findByItemIdOrderByAmountDesc("1")).thenReturn(Collections.singletonList(sampleBid));

        Item item = itemService.getItemById("1");
        assertEquals("Test Item", item.getName());
        assertEquals(150.0, item.getHighestBid());
    }

    @Test
    void getItemById_ShouldThrowException() {
        when(itemRepository.findById("99")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> itemService.getItemById("99"));

        assertEquals("Entity: 99 was not found", exception.getMessage());
    }

    @Test
    void createItem_ShouldSaveAndReturnItem() {
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(sampleItem);
        Item newItem = new Item(null, "Test Item", "Description", 100.0, LocalDateTime.now().plusDays(1), true, "test@example.com", null);
        Item createdItem = itemService.createItem(newItem);
        assertEquals("Test Item", createdItem.getName());
    }

    @Test
    void createItem_ShouldFailWithInvalidEmail() {
        Item newItem = new Item(null, "Invalid Item", "Description", 100.0, LocalDateTime.now().plusDays(1), true, "invalid-email", null);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> itemService.createItem(newItem));
        
        assertEquals("Invalid email format for creator", exception.getMessage());
    }

    @Test
    void deactivateExpiredItems_ShouldUpdateInactiveItems() {
        sampleItem.setEndTime(LocalDateTime.now().minusDays(1));
        List<ItemEntity> activeItems = Arrays.asList(sampleItem);

        when(itemRepository.findByActive(true)).thenReturn(activeItems);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(sampleItem);

        itemService.deactivateExpiredItems();

        assertFalse(sampleItem.isActive());
        verify(itemRepository, times(1)).save(sampleItem);
    }

    @Test
    void getActiveItems_ShouldReturnList() {
        when(itemRepository.findByActive(true)).thenReturn(Collections.singletonList(sampleItem));
        
        List<Item> items = itemService.getActiveItems();
        
        assertEquals(1, items.size());
        assertEquals("Test Item", items.get(0).getName());
    }

    @Test
    void searchItemByName_ShouldReturnItem() {
        when(itemRepository.findByNameContainingIgnoreCase("Test")).thenReturn(Collections.singletonList(sampleItem));
        
        Item item = itemService.searchItemByName("Test");
        
        assertEquals("Test Item", item.getName());
    }

    @Test
    void searchItemByName_ShouldThrowException() {
        when(itemRepository.findByNameContainingIgnoreCase("Unknown"))
            .thenReturn(Collections.emptyList());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> 
            itemService.searchItemByName("Unknown"));

        assertTrue(exception.getMessage().contains("Entity: Unknown was not found"));
    }

    @Test
    void createItem_ShouldFailWithPastEndTime() {
        Item newItem = new Item(null, "Expired Item", "Description", 100.0, 
            LocalDateTime.now().minusDays(1), true, "test@example.com", null);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            itemService.createItem(newItem));
        
        assertEquals("End time must be in the future", exception.getMessage());
    }

    @Test
    void updateItem_ShouldUpdateAndReturnItem() {
        when(itemRepository.findById("1")).thenReturn(Optional.of(sampleItem));
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(sampleItem);
        
        Item updatedItem = new Item("1", "Updated Item", "Updated Description", 
            150.0, LocalDateTime.now().plusDays(2), true, "test@example.com", null);
        
        Item result = itemService.updateItem("1", updatedItem);
        
        assertEquals("Updated Item", result.getName());
        assertEquals(150.0, result.getInitialPrice());
    }

    @Test
    void updateItem_ShouldFailWithInvalidEmail() {
        when(itemRepository.findById("1")).thenReturn(Optional.of(sampleItem));
        
        Item updatedItem = new Item("1", "Updated Item", "Updated Description", 
            150.0, LocalDateTime.now().plusDays(2), true, "invalid-email", null);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            itemService.updateItem("1", updatedItem));
        
        assertEquals("Invalid email format for creator", exception.getMessage());
    }

    @Test
    void updateItem_ShouldFailWithPastEndTimeForActiveItem() {
        when(itemRepository.findById("1")).thenReturn(Optional.of(sampleItem));
        
        Item updatedItem = new Item("1", "Updated Item", "Updated Description", 
            150.0, LocalDateTime.now().minusDays(1), true, "test@example.com", null);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            itemService.updateItem("1", updatedItem));
        
        assertEquals("End time must be in the future for active items", exception.getMessage());
    }

    @Test
    void deleteItem_ShouldRemoveItem() {
        when(itemRepository.findById("1")).thenReturn(Optional.of(sampleItem));
        
        itemService.deleteItem("1");
        
        verify(itemRepository, times(1)).delete(sampleItem);
    }

    @Test
    void deleteItem_ShouldThrowExceptionIfNotFound() {
        when(itemRepository.findById("99")).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(EntityNotFoundException.class, () -> 
            itemService.deleteItem("99"));
        
        assertEquals("Entity: 99 was not found", exception.getMessage());
    }
}
