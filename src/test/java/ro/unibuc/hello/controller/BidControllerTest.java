package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.exception.BidException;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.BidService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BidControllerTest {

    @Mock
    private BidService bidService;

    @InjectMocks
    private BidController bidController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Bid testBid;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bidController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // For LocalDateTime serialization

        testBid = new Bid(
                "bid1",
                "item1",
                "John Doe",
                150.0,
                LocalDateTime.now(),
                "john@example.com"
        );
        testBid.setItemName("Test Item");
    }

    @Test
    void getAllBids_ShouldReturnAllBids() throws Exception {
        // Arrange
        List<Bid> bids = Arrays.asList(
                testBid,
                new Bid("bid2", "item1", "Jane Doe", 200.0, LocalDateTime.now(), "jane@example.com")
        );
        when(bidService.getAllBids()).thenReturn(bids);

        // Act & Assert
        mockMvc.perform(get("/bids"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("bid1"))
                .andExpect(jsonPath("$[1].id").value("bid2"));
    }

    @Test
    void getBidById_ShouldReturnBid_WhenExists() throws Exception {
        // Arrange
        when(bidService.getBidById("bid1")).thenReturn(testBid);

        // Act & Assert
        mockMvc.perform(get("/bids/bid1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("bid1"))
                .andExpect(jsonPath("$.bidderName").value("John Doe"));
    }

    @Test
    void getBidById_ShouldReturnNotFound_WhenNotExists() throws Exception {
        // Arrange
        when(bidService.getBidById("nonexistent")).thenThrow(new EntityNotFoundException("nonexistent"));

        // Act & Assert
        mockMvc.perform(get("/bids/nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBidsByItem_ShouldReturnBidsForItem() throws Exception {
        // Arrange
        List<Bid> bids = Arrays.asList(testBid, new Bid());
        when(bidService.getBidsByItem("item1")).thenReturn(bids);

        // Act & Assert
        mockMvc.perform(get("/bids/item/item1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getBidsByBidder_ShouldReturnBidsFromBidder() throws Exception {
        // Arrange
        List<Bid> bids = Collections.singletonList(testBid);
        when(bidService.getBidsByBidder("John Doe")).thenReturn(bids);

        // Act & Assert
        mockMvc.perform(get("/bids/bidder/John Doe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].bidderName").value("John Doe"));
    }

    @Test
    void placeBid_ShouldReturnCreated_WhenValid() throws Exception {
        // Arrange
        when(bidService.placeBid(any(Bid.class))).thenReturn(testBid);

        // Act & Assert
        mockMvc.perform(post("/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBid)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("bid1"));
    }

    @Test
    void placeBid_ShouldReturnBadRequest_WhenBidException() throws Exception {
        // Arrange
        when(bidService.placeBid(any(Bid.class))).thenThrow(BidException.bidTooLow());

        // Act & Assert
        mockMvc.perform(post("/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void placeBid_ShouldReturnNotFound_WhenEntityNotFound() throws Exception {
        // Arrange
        when(bidService.placeBid(any(Bid.class))).thenThrow(new EntityNotFoundException("item1"));

        // Act & Assert
        mockMvc.perform(post("/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBid)))
                .andExpect(status().isNotFound());
    }

    @Test
    void placeBid_ShouldReturnBadRequest_WhenIllegalArgument() throws Exception {
        // Arrange
        when(bidService.placeBid(any(Bid.class))).thenThrow(new IllegalArgumentException("Invalid email"));

        // Act & Assert
        mockMvc.perform(post("/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteBid_ShouldReturnNoContent_WhenExists() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/bids/bid1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBid_ShouldReturnNotFound_WhenNotExists() throws Exception {
        // Arrange
        doThrow(new EntityNotFoundException("nonexistent")).when(bidService).deleteBid("nonexistent");

        // Act & Assert
        mockMvc.perform(delete("/bids/nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBidsByEmail_ShouldReturnBidsForEmail() throws Exception {
        // Arrange
        List<Bid> bids = Collections.singletonList(testBid);
        when(bidService.getBidsByEmail("john@example.com")).thenReturn(bids);

        // Act & Assert
        mockMvc.perform(get("/bids/bidder-email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }
}