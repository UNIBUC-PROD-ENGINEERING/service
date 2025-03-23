package ro.unibuc.hello.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.AuctionPlaceBidRequest;
import ro.unibuc.hello.dto.AuctionPost;
import ro.unibuc.hello.dto.AuctionPut;
import ro.unibuc.hello.dto.AuctionWithAuctioneerAndItem;
import ro.unibuc.hello.dto.BidWithBidder;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.GlobalExceptionHandler;
import ro.unibuc.hello.permissions.AuctionPermissionChecker;
import ro.unibuc.hello.service.AuctionsService;
import ro.unibuc.hello.service.SessionsService;

public class AuctionsControllerTest {

    @Mock
    private AuctionsService auctionsService;

    @Mock
    private AuctionPermissionChecker permissionChecker;

    @Mock
    private HttpServletRequest request;

    @Mock
    private SessionsService sessionsService;

    @InjectMocks
    private AuctionsController auctionsController;

    @InjectMocks
    private AuthInterceptor authInterceptor;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(auctionsController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .addInterceptors(authInterceptor)
            .build();

        when(sessionsService.getValidSession(anyString())).thenReturn(new SessionEntity("1", new UserEntity("11", "user 1", "username1", "password1"), null));
    }

    @Test
    void testGetAll() throws Exception {
        // Arrange
        User user1 = new User("11", "user 1");
        User user2 = new User("12", "user 2");
        Item item1 = new Item("21", "Item 1", "description 1");
        Item item2 = new Item("22", "Item 2", "description 2");
        List<AuctionWithAuctioneerAndItem> auctions = Arrays.asList(
            new AuctionWithAuctioneerAndItem("1", "Title 1", "Description 1", 10, "open", user1, item1),
            new AuctionWithAuctioneerAndItem("2", "Title 2", "Description 2", 20, "closed", user2, item2)
        );
        when(auctionsService.getAllAuctions()).thenReturn(auctions);

        // Act & Assert
        mockMvc.perform(get("/auctions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].title").value("Title 1"))
            .andExpect(jsonPath("$[0].description").value("Description 1"))
            .andExpect(jsonPath("$[0].startPrice").value(10))
            .andExpect(jsonPath("$[0].status").value("open"))
            .andExpect(jsonPath("$[0].auctioneer.id").value("11"))
            .andExpect(jsonPath("$[0].auctioneer.name").value("user 1"))
            .andExpect(jsonPath("$[0].item.id").value("21"))
            .andExpect(jsonPath("$[0].item.name").value("Item 1"))
            .andExpect(jsonPath("$[0].item.description").value("description 1"))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[1].title").value("Title 2"))
            .andExpect(jsonPath("$[1].description").value("Description 2"))
            .andExpect(jsonPath("$[1].startPrice").value(20))
            .andExpect(jsonPath("$[1].status").value("closed"))
            .andExpect(jsonPath("$[1].auctioneer.id").value("12"))
            .andExpect(jsonPath("$[1].auctioneer.name").value("user 2"))
            .andExpect(jsonPath("$[1].item.id").value("22"))
            .andExpect(jsonPath("$[1].item.name").value("Item 2"))
            .andExpect(jsonPath("$[1].item.description").value("description 2"));
    }

    @Test
    void testGetAuctionById() throws Exception {
        // Arrange
        User user1 = new User("11", "user 1");
        Item item1 = new Item("21", "Item 1", "description 1");
        AuctionWithAuctioneerAndItem auction = new AuctionWithAuctioneerAndItem("1", "Title 1", "Description 1", 10, "open", user1, item1);
        when(auctionsService.getAuctionById("1")).thenReturn(auction);

        // Act & Assert
        mockMvc.perform(get("/auctions/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.title").value("Title 1"))
            .andExpect(jsonPath("$.description").value("Description 1"))
            .andExpect(jsonPath("$.startPrice").value(10))
            .andExpect(jsonPath("$.status").value("open"))
            .andExpect(jsonPath("$.auctioneer.id").value("11"))
            .andExpect(jsonPath("$.auctioneer.name").value("user 1"))
            .andExpect(jsonPath("$.item.id").value("21"))
            .andExpect(jsonPath("$.item.name").value("Item 1"))
            .andExpect(jsonPath("$.item.description").value("description 1"));
    }

    @Test
    void testGetAuctionById_cascadesException() throws Exception {
        // Arrange
        when(auctionsService.getAuctionById("1")).thenThrow(new EntityNotFoundException("Auction not found"));

        // Act & Assert
        mockMvc.perform(get("/auctions/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Auction not found"));
    }

    @Test
    void testGetAuctionHighestBid() throws Exception {
        // Arrange
        User user1 = new User("11", "user 1");
        BidWithBidder bid = new BidWithBidder("31", 10, user1);
        when(auctionsService.getAuctionHighestBid("1")).thenReturn(bid);

        // Act & Assert
        mockMvc.perform(get("/auctions/1/highest-bid"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("31"))
            .andExpect(jsonPath("$.price").value(10))
            .andExpect(jsonPath("$.bidder.id").value("11"))
            .andExpect(jsonPath("$.bidder.name").value("user 1"));
    }

    @Test
    void testGetAuctionBids() throws Exception {
        // Arrange
        User user1 = new User("11", "user 1");
        User user2 = new User("12", "user 2");
        List<BidWithBidder> bids = Arrays.asList(
            new BidWithBidder("31", 10, user1),
            new BidWithBidder("32", 20, user2)
        );
        when(auctionsService.getAuctionBids("1")).thenReturn(bids);

        // Act & Assert
        mockMvc.perform(get("/auctions/1/bids"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("31"))
            .andExpect(jsonPath("$[0].price").value(10))
            .andExpect(jsonPath("$[0].bidder.id").value("11"))
            .andExpect(jsonPath("$[0].bidder.name").value("user 1"))
            .andExpect(jsonPath("$[1].id").value("32"))
            .andExpect(jsonPath("$[1].price").value(20))
            .andExpect(jsonPath("$[1].bidder.id").value("12"))
            .andExpect(jsonPath("$[1].bidder.name").value("user 2"));
    }

    @Test
    void testCreate() throws Exception {
        // Arrange
        User user1 = new User("11", "user 1");
        Item item1 = new Item("21", "Item 1", "description 1");
        AuctionWithAuctioneerAndItem auction = new AuctionWithAuctioneerAndItem("1", "Title 1", "Description 1", 10, "open", user1, item1);
        when(auctionsService.saveAuction(anyString(), any(AuctionPost.class))).thenReturn(auction);

        // Act & Assert
        mockMvc.perform(post("/auctions")
            .content("{\"title\":\"Title 1\",\"description\":\"Description 1\",\"startPrice\":10, \"itemId\":\"21\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-Session-Id", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.title").value("Title 1"))
            .andExpect(jsonPath("$.description").value("Description 1"))
            .andExpect(jsonPath("$.startPrice").value(10))
            .andExpect(jsonPath("$.status").value("open"))
            .andExpect(jsonPath("$.auctioneer.id").value("11"))
            .andExpect(jsonPath("$.auctioneer.name").value("user 1"))
            .andExpect(jsonPath("$.item.id").value("21"))
            .andExpect(jsonPath("$.item.name").value("Item 1"))
            .andExpect(jsonPath("$.item.description").value("description 1"));
    }

    @Test
    void testUpdateAuction() throws Exception {
        // Arrange
        User user1 = new User("11", "user 1");
        Item item1 = new Item("21", "Item 1", "description 1");
        AuctionWithAuctioneerAndItem auction = new AuctionWithAuctioneerAndItem("1", "Title updated", "Description updated", 10, "open", user1, item1);
        when(auctionsService.updateAuction(anyString(), any(AuctionPut.class))).thenReturn(auction);

        // Act & Assert
        mockMvc.perform(put("/auctions/1")
            .content("{\"title\":\"Title updated\",\"description\":\"Description updated\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-Session-Id", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.title").value("Title updated"))
            .andExpect(jsonPath("$.description").value("Description updated"))
            .andExpect(jsonPath("$.startPrice").value(10))
            .andExpect(jsonPath("$.status").value("open"))
            .andExpect(jsonPath("$.auctioneer.id").value("11"))
            .andExpect(jsonPath("$.auctioneer.name").value("user 1"))
            .andExpect(jsonPath("$.item.id").value("21"))
            .andExpect(jsonPath("$.item.name").value("Item 1"))
            .andExpect(jsonPath("$.item.description").value("description 1"));
    }

    @Test
    void testPlaceBid() throws Exception {
        // Arrange
        User user1 = new User("11", "user 1");
        BidWithBidder bid = new BidWithBidder("31", 10, user1);
        when(auctionsService.placeBid(anyString(), anyString(), any(AuctionPlaceBidRequest.class))).thenReturn(bid);

        // Act & Assert
        mockMvc.perform(post("/auctions/1/place-bid")
            .content("{\"price\":10}")
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-Session-Id", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("31"))
            .andExpect(jsonPath("$.price").value(10))
            .andExpect(jsonPath("$.bidder.id").value("11"))
            .andExpect(jsonPath("$.bidder.name").value("user 1"));
    }

    @Test
    void testCloseAuction() throws Exception {
        // Act
        mockMvc.perform(post("/auctions/1/close")
            .header("X-Session-Id", "1"))
            .andExpect(status().isOk());

        // Assert
        verify(auctionsService, times(1)).closeAuction("1");
    }

    @Test
    void testDeleteAuction() throws Exception {
        // Act
        mockMvc.perform(delete("/auctions/1")
            .header("X-Session-Id", "1"))
            .andExpect(status().isOk());

        // Assert
        verify(auctionsService, times(1)).deleteAuction("1");
    }
}
