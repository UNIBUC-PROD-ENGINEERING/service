package ro.unibuc.hello.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.http.HttpServletRequest;
import ro.unibuc.hello.auth.AuthInterceptor;
import ro.unibuc.hello.auth.AuthUtil;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.Auction;
import ro.unibuc.hello.dto.AuctionPlaceBidRequest;
import ro.unibuc.hello.dto.AuctionPost;
import ro.unibuc.hello.dto.AuctionPut;
import ro.unibuc.hello.dto.AuctionWithAuctioneerAndItem;
import ro.unibuc.hello.dto.AuctionWithItem;
import ro.unibuc.hello.dto.BidWithAuction;
import ro.unibuc.hello.dto.BidWithBidder;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.ItemPostRequest;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserPostRequest;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.GlobalExceptionHandler;
import ro.unibuc.hello.permissions.AuctionPermissionChecker;
import ro.unibuc.hello.service.AuctionsService;
import ro.unibuc.hello.service.UsersService;

public class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @Mock
    private AuctionPermissionChecker permissionChecker;

    @Mock
    private HttpServletRequest request;

    // @Mock
    // private SessionsService sessionsService;

    @InjectMocks
    private UsersController usersController;

    @InjectMocks
    private AuthInterceptor authInterceptor;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    void testGetAllUsers() throws Exception {
        // Arrange
        User user1 = new User("1", "User One");
        User user2 = new User("2", "User Two");
        List<User> users = Arrays.asList(user1, user2);
        when(usersService.getAllUsers()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].name").value("User One"))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[1].name").value("User Two"));
    }

    @Test
    void testGetUserById() throws Exception {
        // Arrange
        User user = new User("1", "User One");
        when(usersService.getUserById("1")).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.name").value("User One"));
    }

    @Test
    void testGetUserItems() throws Exception {
        // Arrange
        Item item1 = new Item("1", "Item One", "Description One");
        Item item2 = new Item("2", "Item Two", "Description Two");
        List<Item> items = Arrays.asList(item1, item2);
        when(usersService.getUserItems("1")).thenReturn(items);

        // Act & Assert
        mockMvc.perform(get("/users/1/items"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].name").value("Item One"))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[1].name").value("Item Two"));
    }

    @Test
    void testGetUserAuctions() throws Exception {
        // Arrange
        Item item = new Item("1", "Item One", "Description One");
        AuctionWithItem auction = new AuctionWithItem("1", "Auction 1", "Description Auction 1", 100, "open", item);
        List<AuctionWithItem> auctions = Arrays.asList(auction);
        when(usersService.getUserAuctions("1")).thenReturn(auctions);
    
        // Act & Assert
        mockMvc.perform(get("/users/1/auctions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].title").value("Auction 1"))
            .andExpect(jsonPath("$[0].description").value("Description Auction 1"))
            .andExpect(jsonPath("$[0].startPrice").value(100))
            .andExpect(jsonPath("$[0].status").value("open"))
            .andExpect(jsonPath("$[0].item.id").value("1"))
            .andExpect(jsonPath("$[0].item.name").value("Item One"));
    }

    @Test
    void testGetUserBids() throws Exception {
        // Arrange
        User user = new User("1", "User One");
        Auction auction = new Auction("1", "Auction 1", "Description Auction 1", 100, "open");
    
        // Create BidWithAuction objects
        List<BidWithAuction> bids = Arrays.asList(
            new BidWithAuction("31", 10, auction),  // First bid
            new BidWithAuction("32", 20, auction)   // Second bid
        );
    
        // Mocking the service method to return BidWithAuction
        when(usersService.getUserBids("1")).thenReturn(bids);
    
        // Act & Assert
        mockMvc.perform(get("/users/1/bids"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("31")) // First bid ID
            .andExpect(jsonPath("$[0].price").value(10)) // First bid price
            .andExpect(jsonPath("$[0].auction.id").value("1")) // Auction ID for the first bid
            .andExpect(jsonPath("$[0].auction.title").value("Auction 1")) // Auction title for the first bid
            
            .andExpect(jsonPath("$[1].id").value("32")) // Second bid ID
            .andExpect(jsonPath("$[1].price").value(20)) // Second bid price
            .andExpect(jsonPath("$[1].auction.id").value("1")) // Auction ID for the second bid
            .andExpect(jsonPath("$[1].auction.title").value("Auction 1")); // Auction title for the second bid
    }
    
 @Test
    void testCreateUser() throws Exception {
        // Arrange
        // UserPostRequest userRequest = new UserPostRequest("User One", "userone@example.com", "password123");
        User createdUser = new User("1", "name");

        when(usersService.saveUser(any(UserPostRequest.class))).thenReturn(createdUser);

        // Act & Assert
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("name"));
    }

    //  @Test
    // void testUpdateUser() throws Exception {
    //     // Arrange
    //    // UserPostRequest userRequest = new UserPostRequest("User One Updated", "useroneupdated@example.com", "newpassword123");
    //     User updatedUser = new User("1", "name");

    //     //when(request.getAttribute(AuthUtil.AUTHENTICATED_USER_ID)).thenReturn("1");
    //     when(usersService.updateUser(eq("1"), any(UserPostRequest.class))).thenReturn(updatedUser);

    //     // Act & Assert
    //     mockMvc.perform(put("/users/1")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("{\"name\": \"name\"}"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.id").value("1"))
    //             .andExpect(jsonPath("$.name").value("name"));
    // }
    
    // @Test
    // void testDeleteUser() throws Exception {
    //     // Arrange
    //     String userId = "1";

    //     doNothing().when(usersService).deleteUser(eq(userId));

    //     // Act & Assert
    //     mockMvc.perform(delete("/users/1"))
    //             .andExpect(status().isOk());

    //     verify(usersService, times(1)).deleteUser(eq(userId));
    // }
    
}
