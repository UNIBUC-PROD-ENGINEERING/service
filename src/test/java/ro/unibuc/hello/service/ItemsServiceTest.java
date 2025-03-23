package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.ItemPost;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ItemsServiceTest {

    @Mock
    private ItemRepository itemRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private SessionService sessionService;

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
        
        List<Item> result = itemsService.getAllItems();
        
        assertEquals(2, result.size());
        assertEquals("Item1", result.get(0).getName());
        assertEquals("Item2", result.get(1).getName());
    }

    @Test
    void testGetItemById_ExistingItem() {
        UserEntity user = new UserEntity("testUser", "password", "username");
        ItemEntity item = new ItemEntity("Item1", "Description1", user);
        when(itemRepository.findById("1")).thenReturn(Optional.of(item));
        Item result = itemsService.getItemById("1");
        assertNotNull(result);
        assertEquals("Item1", result.getName());
        assertEquals("Description1", result.getDescription());
        assertEquals("username", result.getOwner());
    }

   // Check if getItemById() throws EntityNotFoundException when an item with a certain ID is not found in db
   @Test
    void testGetItemById_NonExistingItem() {
        when(itemRepository.findById("1")).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> itemsService.getItemById("1"));
    }

    @Test
    void testGetItemsOwnedBy() {
        // Arrange
        UserEntity owner = new UserEntity("testUser", "password", "username");
        List<ItemEntity> items = Arrays.asList(
                new ItemEntity("Item1", "Description1", owner),
                new ItemEntity("Item2", "Description2", owner)
        );
        when(itemRepository.findByOwner(owner)).thenReturn(items);
        // Act
        List<Item> result = itemsService.getItemsOwnedBy(owner);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Item1", result.get(0).getName());
        assertEquals("Item2", result.get(1).getName());
        assertEquals("username", result.get(0).getOwner());
        assertEquals("username", result.get(1).getOwner());
    }
    
    @Test
    void testSaveItem() {
        // Arrange
        String sessionId = "session123"; 
        String itemName = "Item1";
        String itemDescription = "Description1";
        
        ItemPost itemPost = new ItemPost(sessionId, itemName, itemDescription);
        UserEntity user = new UserEntity("testUser", "password", "username");
    
        SessionEntity session = new SessionEntity(user, LocalDateTime.now().plusMinutes(30));
    
        when(sessionService.getValidSession(sessionId)).thenReturn(session); 
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user)); 
        ItemEntity newItemEntity = new ItemEntity(itemName, itemDescription, user);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(newItemEntity);
    
        // Act 
        Item savedItem = itemsService.saveItem(itemPost);
    
        // Assert 
        assertNotNull(savedItem); 
        assertEquals(itemName, savedItem.getName()); 
        assertEquals(itemDescription, savedItem.getDescription()); 
        assertEquals(user.getUsername(), savedItem.getOwner()); 
        verify(itemRepository, times(1)).save(any(ItemEntity.class));  
    }
    
    @Test
    void testSaveAll() {
        // Arrange
        String sessionId = "session123";
        String itemName1 = "Item1";
        String itemDescription1 = "Description1";
        String itemName2 = "Item2";
        String itemDescription2 = "Description2";
        
        // Create two ItemPost objects to be saved
        ItemPost itemPost1 = new ItemPost(sessionId, itemName1, itemDescription1);
        ItemPost itemPost2 = new ItemPost(sessionId,itemName2, itemDescription2);
        
        List<ItemPost> items = Arrays.asList(itemPost1, itemPost2);
        
        // Create a UserEntity that would be associated with both items
        UserEntity user = new UserEntity("testUser", "password", "username");
        
        // Create a SessionEntity that is valid and associated with the user
        SessionEntity session = new SessionEntity(user, LocalDateTime.now().plusMinutes(30));
        
        // Mock the sessionService to return a valid session
        when(sessionService.getValidSession(sessionId)).thenReturn(session);
        
        // Mock the userRepository to return the user when searched by ID
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        
        // Create ItemEntity objects that would be saved in the repository
        ItemEntity itemEntity1 = new ItemEntity(itemName1, itemDescription1, user);
        ItemEntity itemEntity2 = new ItemEntity(itemName2, itemDescription2, user);
        
        // Mock the saveAll method of itemRepository to return the saved entities
        when(itemRepository.saveAll(anyList())).thenReturn(Arrays.asList(itemEntity1, itemEntity2));
        
        // Act
        List<Item> savedItems = itemsService.saveAll(items);
        
        // Assert
        assertNotNull(savedItems);
        assertEquals(2, savedItems.size());  // Ensure we have two items saved
        
        // Verify that the first item has correct details
        Item savedItem1 = savedItems.get(0);
        assertEquals(itemName1, savedItem1.getName());
        assertEquals(itemDescription1, savedItem1.getDescription());
        assertEquals(user.getUsername(), savedItem1.getOwner());

        // Verify that the second item has correct details
        Item savedItem2 = savedItems.get(1);
        assertEquals(itemName2, savedItem2.getName());
        assertEquals(itemDescription2, savedItem2.getDescription());
        assertEquals(user.getUsername(), savedItem2.getOwner());
    }

    @Test
    void testUpdateItem_ExistingEntity() throws EntityNotFoundException {
        // Arrange
        String itemId = "item123";
        String newItemName = "Updated Item";
        String newItemDescription = "Updated Description";
        String existingOwnerName = "existingUser";
        String existingOwnerUserame = "existingUsername";
        UserEntity existingUser = new UserEntity(existingOwnerName, "password", existingOwnerUserame);
        ItemEntity existingItemEntity = new ItemEntity("Old Item", "Old Description", existingUser);
        
        // Create a new Item object that will be used for the update
        Item itemToUpdate = new Item(newItemName, newItemDescription, existingOwnerName);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(existingItemEntity));
        when(userRepository.findByUsername(existingOwnerName)).thenReturn(existingUser);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(existingItemEntity);
        
        // Act
        Item updatedItem = itemsService.updateItem(itemId, itemToUpdate);
        
        // Assert
        assertNotNull(updatedItem);
        assertEquals(newItemName, updatedItem.getName());  // Ensure name is updated
        assertEquals(newItemDescription, updatedItem.getDescription());  // Ensure description is updated
        assertEquals(existingOwnerUserame, updatedItem.getOwner());  // Ensure owner remains the same
    }

    @Test
    void testUpdateItem_NonExistingEntity() {
        // Arrange
        String itemId = "item123";
        String newItemName = "Updated Item";
        String newItemDescription = "Updated Description";
        String existingOwnerUsername = "existingUser";
        
        // Create a new Item object that will be used for the update
        Item itemToUpdate = new Item(newItemName, newItemDescription, existingOwnerUsername);
        
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
