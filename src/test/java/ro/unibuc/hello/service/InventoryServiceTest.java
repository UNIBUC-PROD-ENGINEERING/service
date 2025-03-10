package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.InventoryEntity;
import ro.unibuc.hello.data.InventoryRepository;
import ro.unibuc.hello.dto.InventoryDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInventoryItems() {
        List<InventoryEntity> entities = Arrays.asList(
                new InventoryEntity("item1", 50, 10),
                new InventoryEntity("item2", 30, 5)
        );
        when(inventoryRepository.findAll()).thenReturn(entities);

        List<InventoryDTO> inventoryItems = inventoryService.getAllInventoryItems();

        assertEquals(2, inventoryItems.size());
        assertEquals("item1", inventoryItems.get(0).getName());
        assertEquals("item2", inventoryItems.get(1).getName());
    }

    @Test
    void testGetInventoryItemById_ExistingEntity() throws EntityNotFoundException {
        String id = "1";
        InventoryEntity entity = new InventoryEntity("item1", 50, 10);
        entity.setId(id);
        when(inventoryRepository.findById(id)).thenReturn(Optional.of(entity));

        InventoryDTO inventoryItem = inventoryService.getInventoryItemById(id);

        assertNotNull(inventoryItem);
        assertEquals(id, inventoryItem.getId());
        assertEquals("item1", inventoryItem.getName());
    }

    @Test
    void testGetInventoryItemById_NonExistingEntity() {
        String id = "NonExistingId";
        when(inventoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.getInventoryItemById(id));
    }

    @Test
    void testCreateInventoryItem() {
        InventoryDTO inventoryDTO = new InventoryDTO(null, "item1", 50, 10);
        InventoryEntity entity = new InventoryEntity("item1", 50, 10);
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        InventoryDTO createdInventoryItem = inventoryService.createInventoryItem(inventoryDTO);

        assertNotNull(createdInventoryItem);
        assertEquals("item1", createdInventoryItem.getName());
    }

    @Test
    void testUpdateInventoryStock_ExistingEntity() throws EntityNotFoundException {
        String id = "1";
        Integer stock = 60;
        InventoryEntity entity = new InventoryEntity("item1", 50, 10);
        entity.setId(id);
        when(inventoryRepository.findById(id)).thenReturn(Optional.of(entity));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        InventoryDTO updatedInventoryItem = inventoryService.updateInventoryStock(id, stock);

        assertNotNull(updatedInventoryItem);
        assertEquals(stock, updatedInventoryItem.getStock());
    }

    @Test
    void testUpdateInventoryStock_NonExistingEntity() {
        String id = "NonExistingId";
        Integer stock = 60;
        when(inventoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.updateInventoryStock(id, stock));
    }

    @Test
    void testDeleteInventoryItem_ExistingEntity() throws EntityNotFoundException {
        String id = "1";
        InventoryEntity entity = new InventoryEntity("item1", 50, 10);
        entity.setId(id);
        when(inventoryRepository.findById(id)).thenReturn(Optional.of(entity));

        inventoryService.deleteInventoryItem(id);

        verify(inventoryRepository, times(1)).delete(entity);
    }

    @Test
    void testDeleteInventoryItem_NonExistingEntity() {
        String id = "NonExistingId";
        when(inventoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.deleteInventoryItem(id));
    }
}
