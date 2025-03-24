package ro.unibuc.hello.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.ItemPostRequest;
import ro.unibuc.hello.dto.ItemWithOwner;
import ro.unibuc.hello.exception.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
class ItemsServiceTest {

    @Mock
    private ItemRepository itemRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private SessionsService sessionsService;

    @InjectMocks
    private ItemsService itemsService = new ItemsService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllItems() {
        UserEntity user = new UserEntity("testUser", "password", "username");
        List<ItemEntity> items = Arrays.asList(
                new ItemEntity("Item1", "Description1", user),
                new ItemEntity("Item2", "Description2", user)
        );
        when(itemRepository.findAll()).thenReturn(items);
        
        List<ItemWithOwner> result = itemsService.getAllItems();
        
        assertEquals(2, result.size());
        assertEquals("Item1", result.get(0).getName());
        assertEquals("Item2", result.get(1).getName());
    }

    @Test
    void testGetItemById_ExistingItem() {
        UserEntity user = new UserEntity("testUser", "password", "username");
        ItemEntity item = new ItemEntity("Item1", "Description1", user);
        when(itemRepository.findById("1")).thenReturn(Optional.of(item));
        ItemWithOwner result = itemsService.getItemById("1");
        assertNotNull(result);
        assertEquals("Item1", result.getName());
        assertEquals("Description1", result.getDescription());
        assertEquals("testUser", result.getOwner().getName());
    }

   // Check if getItemById() throws EntityNotFoundException when an item with a certain ID is not found in db
   @Test
    void testGetItemById_NonExistingItem() {
        when(itemRepository.findById("1")).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> itemsService.getItemById("1"));
    }
    
    @Test
    void testSaveItem() {
        // Arrange
        String ownerId = "11";
        String itemName = "Item1";
        String itemDescription = "Description1";
        
        ItemPostRequest itemPost = new ItemPostRequest(itemName, itemDescription);
        UserEntity user = new UserEntity(ownerId, "testUser", "password", "username");
    
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user)); 
        ItemEntity newItemEntity = new ItemEntity(itemName, itemDescription, user);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(newItemEntity);
    
        // Act 
        ItemWithOwner savedItem = itemsService.saveItem(ownerId, itemPost);
    
        // Assert 
        assertNotNull(savedItem); 
        assertEquals(itemName, savedItem.getName()); 
        assertEquals(itemDescription, savedItem.getDescription()); 
        assertEquals(user.getName(), savedItem.getOwner().getName()); 
        verify(itemRepository, times(1)).save(any(ItemEntity.class));  
    }
    
    @Test
    void testSaveAll() {
        // Arrange
        String ownerId = "12";
        String itemName1 = "Item1";
        String itemDescription1 = "Description1";
        String itemName2 = "Item2";
        String itemDescription2 = "Description2";
        
        // Create two ItemPost objects to be saved
        ItemPostRequest itemPost1 = new ItemPostRequest(itemName1, itemDescription1);
        ItemPostRequest itemPost2 = new ItemPostRequest(itemName2, itemDescription2);
        
        List<ItemPostRequest> items = Arrays.asList(itemPost1, itemPost2);
        
        // Create a UserEntity that would be associated with both items
        UserEntity user = new UserEntity(ownerId, "testUser", "password", "username");
        
        // Mock the userRepository to return the user when searched by ID
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        
        // Create ItemEntity objects that would be saved in the repository
        ItemEntity itemEntity1 = new ItemEntity(itemName1, itemDescription1, user);
        ItemEntity itemEntity2 = new ItemEntity(itemName2, itemDescription2, user);
        
        // Mock the saveAll method of itemRepository to return the saved entities
        when(itemRepository.saveAll(anyList())).thenReturn(Arrays.asList(itemEntity1, itemEntity2));
        
        // Act
        List<ItemWithOwner> savedItems = itemsService.saveAll(ownerId, items);
        
        // Assert
        assertNotNull(savedItems);
        assertEquals(2, savedItems.size());  // Ensure we have two items saved
        
        // Verify that the first item has correct details
        ItemWithOwner savedItem1 = savedItems.get(0);
        assertEquals(itemName1, savedItem1.getName());
        assertEquals(itemDescription1, savedItem1.getDescription());
        assertEquals(user.getName(), savedItem1.getOwner().getName());

        // Verify that the second item has correct details
        ItemWithOwner savedItem2 = savedItems.get(1);
        assertEquals(itemName2, savedItem2.getName());
        assertEquals(itemDescription2, savedItem2.getDescription());
        assertEquals(user.getName(), savedItem2.getOwner().getName());
    }

    @Test
    void testUpdateItem_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String itemId = "item123";
        String newItemName = "Updated Item";
        String newItemDescription = "Updated Description";
        String existingOwnerId = "11";
        String existingOwnerName = "existingUser";
        String existingOwnerUserame = "existingUsername";
        UserEntity existingUser = new UserEntity(existingOwnerId, existingOwnerName, "password", existingOwnerUserame);
        ItemEntity existingItemEntity = new ItemEntity("Old Item", "Old Description", existingUser);
        
        // Create a new Item object that will be used for the update
        ItemPostRequest itemToUpdate = new ItemPostRequest(newItemName, newItemDescription);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(existingItemEntity));
        when(userRepository.findById(existingOwnerId)).thenReturn(Optional.of(existingUser));
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(existingItemEntity);
        
        // Act
        ItemWithOwner updatedItem = itemsService.updateItem(itemId, itemToUpdate);
        
        // Assert
        assertNotNull(updatedItem);
        assertEquals(newItemName, updatedItem.getName());  // Ensure name is updated
        assertEquals(newItemDescription, updatedItem.getDescription());  // Ensure description is updated
        assertEquals(existingOwnerName, updatedItem.getOwner().getName());  // Ensure owner remains the same
    }

    @Test
    void testUpdateItem_NonExistingEntity() {
        // Arrange
        String itemId = "item123";
        String newItemName = "Updated Item";
        String newItemDescription = "Updated Description";
        
        // Create a new Item object that will be used for the update
        ItemPostRequest itemToUpdate = new ItemPostRequest(newItemName, newItemDescription);
        
        // Mock the repository to return empty when searching by id (item not found)
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            itemsService.updateItem(itemId, itemToUpdate);  // This should throw EntityNotFoundException
        });
    }

    @Test
    void testDeleteItem_ExistingEntit() throws EntityNotFoundException {
        // Arrange
        String itemId = "item123";
        
        // Create a mock ItemEntity to be deleted
        ItemEntity existingItemEntity = new ItemEntity("Item Name", "Item Description", null);
        
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(existingItemEntity));
        
        // Act
        itemsService.deleteItem(itemId);
        
        // Assert
        // Verify that itemRepository.findById was called with the given itemId
        verify(itemRepository, times(1)).findById(itemId);
        
        // Verify that itemRepository.delete was called exactly once to delete the item
        verify(itemRepository, times(1)).delete(existingItemEntity);
    }

    @Test
    void testDeleteItemNotFound_NonExistingEntit() {
        // Arrange
        String itemId = "item123";
        
        // Mock the repository to return empty when searching by id (item not found)
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            itemsService.deleteItem(itemId);  // This should throw EntityNotFoundException
        });
        
        // Verify that findById was called exactly once
        verify(itemRepository, times(1)).findById(itemId);
        
        // Verify that delete was never called since the item was not found
        verify(itemRepository, times(0)).delete(any(ItemEntity.class));
    }

    @Test
    void testDeleteAllItems() {
        // Act
        itemsService.deleteAllItems();
        
        // Assert
        // Verify that itemRepository.deleteAll() was called exactly once
        verify(itemRepository, times(1)).deleteAll();
    }
}
