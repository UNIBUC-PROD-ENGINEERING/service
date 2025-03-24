package ro.unibuc.hello.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import ro.unibuc.hello.data.AuctionEntity;
import ro.unibuc.hello.data.AuctionRepository;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.AuctionPlaceBidRequest;
import ro.unibuc.hello.dto.AuctionPost;
import ro.unibuc.hello.dto.AuctionPut;
import ro.unibuc.hello.dto.AuctionWithAuctioneerAndItem;
import ro.unibuc.hello.dto.AuctionWithItem;
import ro.unibuc.hello.dto.BidWithAuction;
import ro.unibuc.hello.dto.BidWithBidder;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.InvalidDataException;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserPostRequest;
public class UsersServiceTest {

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private UsersService usersService = new UsersService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange: Creăm o listă de UserEntity pentru a o returna din UserRepository
        UserEntity user1 = new UserEntity("1", "Name1", "password123", "UserName1");
        UserEntity user2 = new UserEntity("2", "Name2", "password456", "UserName2");
        List<UserEntity> userEntities = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userEntities);

        // Act
        List<User> users = usersService.getAllUsers();

        //Assert
        assertNotNull(users);
        assertEquals(2, users.size()); 
        assertEquals("1", users.get(0).getId()); 
        assertEquals("Name1", users.get(0).getName()); 
        assertEquals("2", users.get(1).getId()); 
        assertEquals("Name2", users.get(1).getName()); 

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_ExistingUser() {
        String userId = "1";
        UserEntity userEntity = new UserEntity(userId, "Name", "password", "username");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));

        User result = usersService.getUserById(userId);

        assertEquals(userId, result.getId());
        assertEquals("Name", result.getName());
    }

    @Test
    void testGetUserById_UserNotFound() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersService.getUserById(userId));
    }

    @Test
    void testGetUserItems_ExistingUser() {
        String userId = "1";
        UserEntity userEntity = new UserEntity(userId, "name", "password", "username");
        ItemEntity item1 = new ItemEntity("Item1", "Description1", userEntity);
        ItemEntity item2 = new ItemEntity("Item2", "Description2", userEntity);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));
        when(itemRepository.findByOwner(userEntity)).thenReturn(Arrays.asList(item1, item2));

        List<Item> items = usersService.getUserItems(userId);

        assertEquals(2, items.size());
        assertEquals("Item1", items.get(0).getName());
        assertEquals("Item2", items.get(1).getName());
    }

    @Test
    void testGetUserItems_UserNotFound() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersService.getUserItems(userId));
    }

    @Test
    void testGetUserAuctions_ExistingUser() {
        String userId = "1";
        UserEntity userEntity = new UserEntity(userId, "name", "password", "username");
        ItemEntity item = new ItemEntity("Item1", "Description of Item1", userEntity);
        AuctionEntity auction1 = new AuctionEntity("Auction1", "Description1", 50, true, item, userEntity);
        AuctionEntity auction2 = new AuctionEntity("Auction2", "Description2", 100, true, item, userEntity);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));
        when(auctionRepository.findByAuctioneer(userEntity)).thenReturn(Arrays.asList(auction1, auction2));

        List<AuctionWithItem> auctions = usersService.getUserAuctions(userId);

        assertEquals(2, auctions.size());
        assertEquals("Auction1", auctions.get(0).getTitle());
        assertEquals("Auction2", auctions.get(1).getTitle());
    }

    @Test
    void testGetUserAuctions_UserNotFound() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersService.getUserAuctions(userId));
    }

    @Test
    void testGetUserBids_ExistingUser() {
        String userId = "1";
        UserEntity userEntity = new UserEntity(userId, "John Doe", "password", "john_doe");
        ItemEntity item = new ItemEntity("Item1", "Description of Item1", userEntity);
        BidEntity bid1 = new BidEntity(100, userEntity, new AuctionEntity("Auction1", "Description1", 50, true, item, userEntity));
        BidEntity bid2 = new BidEntity(200, userEntity, new AuctionEntity("Auction2", "Description2", 100, true, item, userEntity));

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));
        when(bidRepository.findByBidder(userEntity)).thenReturn(Arrays.asList(bid1, bid2));

        List<BidWithAuction> bids = usersService.getUserBids(userId);

        assertEquals(2, bids.size());
        assertEquals(100, bids.get(0).getPrice());
        assertEquals(200, bids.get(1).getPrice());
    }

    @Test
    void testGetUserBids_UserNotFound() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersService.getUserBids(userId));
    }

    @Test
    void testSaveUser() {
        String name = "name";
        String username = "username";
        String password = "password";
        UserPostRequest userPostRequest = new UserPostRequest(name, username, password);

        UserEntity newUserEntity = new UserEntity(name, password, username);
        when(userRepository.save(any(UserEntity.class))).thenReturn(newUserEntity);

        User savedUser = usersService.saveUser(userPostRequest);

        assertEquals(name, savedUser.getName());
    }

    @Test
    void testSaveUser_UsernameAlreadyExists() {
        String name = "name";
        String username = "username";
        String password = "password";
        UserPostRequest userPostRequest = new UserPostRequest(name, username, password);

        when(userRepository.save(any(UserEntity.class)))
            .thenThrow(new InvalidDataException("Username already exists"));

        assertThrows(InvalidDataException.class, () -> usersService.saveUser(userPostRequest));
    }

    @Test
    void testSaveAllUsers() {
        List<UserPostRequest> userPostRequests = Arrays.asList(
            new UserPostRequest("Nume1", "nume1", "password1"),
            new UserPostRequest("Nume2", "nume2", "password2")
        );

        UserEntity userEntity1 = new UserEntity("Nume1", "password1", "nume1");
        UserEntity userEntity2 = new UserEntity("Nume2", "password2", "nume2");

        when(userRepository.saveAll(any(List.class))).thenReturn(Arrays.asList(userEntity1, userEntity2));

        List<User> savedUsers = usersService.saveAll(userPostRequests);

        assertEquals(2, savedUsers.size());
        assertEquals("Nume1", savedUsers.get(0).getName());
        assertEquals("Nume2", savedUsers.get(1).getName());
    }

    @Test
    void testUpdateUser() {
        String userId = "1";
        UserPostRequest userPostRequest = new UserPostRequest("UpdatedName", "updatedUsername", "newPassword");

        UserEntity existingUserEntity = new UserEntity("1", "OldName", "oldPassword", "oldUsername");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity(userId, "UpdatedName", "newPassword", "updatedUsername"));

        User updatedUser = usersService.updateUser(userId, userPostRequest);

        assertEquals("UpdatedName", updatedUser.getName());
    }

    @Test
    void testUpdateUserNotFound() {
        String userId = "1";
        UserPostRequest userPostRequest = new UserPostRequest("UpdatedName", "updatedUsername", "newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersService.updateUser(userId, userPostRequest));
    }

    @Test
    void testUpdateUserDuplicateUsername() {
        String userId = "1";
        UserPostRequest userPostRequest = new UserPostRequest("UpdatedName", "updatedUsername", "newPassword");

        UserEntity existingUserEntity = new UserEntity("1", "OldName", "oldPassword", "oldUsername");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(any(UserEntity.class))).thenThrow(new InvalidDataException("Username already exists"));

        assertThrows(InvalidDataException.class, () -> usersService.updateUser(userId, userPostRequest));
    }

    @Test
    void testDeleteUser() {
        String userId = "1";
        UserEntity existingUserEntity = new UserEntity(userId, "UserName", "password", "username");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));

        usersService.deleteUser(userId);

        verify(userRepository, times(1)).delete(existingUserEntity);
    }

    @Test
    void testDeleteUserNotFound() {
        String userId = "1";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // The deletion should throw an EntityNotFoundException if the user doesn't exist
        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> usersService.deleteUser(userId));
    }

    @Test
    void testDeleteAllUsers() {
        // Just verify that deleteAll() is called once
        usersService.deleteAllUsers();

        verify(userRepository, times(1)).deleteAll();
    }
}