package ro.unibuc.hello.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals("99", exception.getMessage());
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
        Exception exception = assertThrows(EntityNotFoundException.class, () -> itemService.getItemById("99"));
        assertEquals("99", exception.getMessage());
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
}
