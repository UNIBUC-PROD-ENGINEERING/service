package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.Category;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.exception.BidException;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BidServiceTest {

    @Mock
    private BidRepository bidRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private BidService bidService;

    private ItemEntity activeItem;
    private ItemEntity inactiveItem;
    private ItemEntity expiredItem;
    private BidEntity bid;
    private Bid bidDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        activeItem = new ItemEntity(
                "Test Item",
                "Description",
                100.0,
                LocalDateTime.now().plusDays(1),
                "creator@example.com",
                Category.ELECTRONICS
        );
        activeItem.setId("item1");

        inactiveItem = new ItemEntity(
                "Inactive Item",
                "Description",
                100.0,
                LocalDateTime.now().plusDays(1),
                "creator@example.com",
                Category.ELECTRONICS
        );
        inactiveItem.setId("item2");
        inactiveItem.setActive(false);

        expiredItem = new ItemEntity(
                "Expired Item",
                "Description",
                100.0,
                LocalDateTime.now().minusDays(1),
                "creator@example.com",
                Category.ELECTRONICS
        );
        expiredItem.setId("item3");

        bid = new BidEntity("item1", "John Doe", 150.0, "john@example.com");
        bid.setId("bid1");

        bidDto = new Bid(
                "bid1",
                "item1",
                "John Doe",
                150.0,
                LocalDateTime.now(),
                "john@example.com"
        );
    }

    @Test
    void getAllBids_ShouldReturnAllBids() {
        // Arrange
        List<BidEntity> bidEntities = Arrays.asList(
                bid,
                new BidEntity("item1", "Jane Doe", 200.0, "jane@example.com")
        );
        when(bidRepository.findAll()).thenReturn(bidEntities);
        when(itemRepository.findById(anyString())).thenReturn(Optional.of(activeItem));

        // Act
        List<Bid> result = bidService.getAllBids();

        // Assert
        assertEquals(2, result.size());
        verify(bidRepository).findAll();
    }

    @Test
    void getBidsByItem_ShouldReturnBidsForItem() {
        // Arrange
        List<BidEntity> bidEntities = Arrays.asList(
                bid,
                new BidEntity("item1", "Jane Doe", 200.0, "jane@example.com")
        );
        when(bidRepository.findByItemId("item1")).thenReturn(bidEntities);
        when(itemRepository.findById(anyString())).thenReturn(Optional.of(activeItem));

        // Act
        List<Bid> result = bidService.getBidsByItem("item1");

        // Assert
        assertEquals(2, result.size());
        verify(bidRepository).findByItemId("item1");
    }

    @Test
    void getBidsByBidder_ShouldReturnBidsFromBidder() {
        // Arrange
        List<BidEntity> bidEntities = Collections.singletonList(bid);
        when(bidRepository.findByBidderName("John Doe")).thenReturn(bidEntities);
        when(itemRepository.findById(anyString())).thenReturn(Optional.of(activeItem));

        // Act
        List<Bid> result = bidService.getBidsByBidder("John Doe");

        // Assert
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getBidderName());
        verify(bidRepository).findByBidderName("John Doe");
    }

    @Test
    void getBidById_ShouldReturnBid_WhenExists() {
        // Arrange
        when(bidRepository.findById("bid1")).thenReturn(Optional.of(bid));
        when(itemRepository.findById(anyString())).thenReturn(Optional.of(activeItem));

        // Act
        Bid result = bidService.getBidById("bid1");

        // Assert
        assertNotNull(result);
        assertEquals("bid1", result.getId());
        verify(bidRepository).findById("bid1");
    }

    @Test
    void getBidById_ShouldThrowException_WhenNotExists() {
        // Arrange
        when(bidRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bidService.getBidById("nonexistent"));
        verify(bidRepository).findById("nonexistent");
    }

    @Test
    void placeBid_ShouldSaveBid_WhenValid() {
        // Arrange
        when(itemRepository.findById("item1")).thenReturn(Optional.of(activeItem));
        when(bidRepository.findByItemIdOrderByAmountDesc("item1")).thenReturn(Collections.emptyList());
        when(bidRepository.findByItemIdAndEmailOrderByAmountDesc("item1", "john@example.com")).thenReturn(Collections.emptyList());
        when(bidRepository.save(any(BidEntity.class))).thenReturn(bid);

        Bid newBid = new Bid();
        newBid.setItemId("item1");
        newBid.setBidderName("John Doe");
        newBid.setAmount(150.0);
        newBid.setEmail("john@example.com");

        // Act
        Bid result = bidService.placeBid(newBid);

        // Assert
        assertNotNull(result);
        assertEquals("bid1", result.getId());
        // The findById method is called twice:
        // 1. In placeBid to validate the item exists
        // 2. In convertToDto to set the item name
        verify(itemRepository, times(2)).findById("item1");
        verify(bidRepository).save(any(BidEntity.class));
    }

    @Test
    void placeBid_ShouldThrowException_WhenItemNotFound() {
        // Arrange
        when(itemRepository.findById("nonexistent")).thenReturn(Optional.empty());

        Bid newBid = new Bid();
        newBid.setItemId("nonexistent");
        newBid.setBidderName("John Doe");
        newBid.setAmount(150.0);
        newBid.setEmail("john@example.com");

        // Act & Assert
        assertThrows(BidException.class, () -> bidService.placeBid(newBid));
        verify(itemRepository).findById("nonexistent");
        verify(bidRepository, never()).save(any(BidEntity.class));
    }

    @Test
    void placeBid_ShouldThrowException_WhenItemNotActive() {
        // Arrange
        when(itemRepository.findById("item2")).thenReturn(Optional.of(inactiveItem));

        Bid newBid = new Bid();
        newBid.setItemId("item2");
        newBid.setBidderName("John Doe");
        newBid.setAmount(150.0);
        newBid.setEmail("john@example.com");

        // Act & Assert
        assertThrows(BidException.class, () -> bidService.placeBid(newBid));
        verify(itemRepository).findById("item2");
        verify(bidRepository, never()).save(any(BidEntity.class));
    }

    @Test
    void placeBid_ShouldThrowException_WhenItemExpired() {
        // Arrange
        when(itemRepository.findById("item3")).thenReturn(Optional.of(expiredItem));

        Bid newBid = new Bid();
        newBid.setItemId("item3");
        newBid.setBidderName("John Doe");
        newBid.setAmount(150.0);
        newBid.setEmail("john@example.com");

        // Act & Assert
        assertThrows(BidException.class, () -> bidService.placeBid(newBid));
        verify(itemRepository).findById("item3");
        verify(bidRepository, never()).save(any(BidEntity.class));
    }

    @Test
    void placeBid_ShouldThrowException_WhenEmailInvalid() {
        // Arrange
        when(itemRepository.findById("item1")).thenReturn(Optional.of(activeItem));

        Bid newBid = new Bid();
        newBid.setItemId("item1");
        newBid.setBidderName("John Doe");
        newBid.setAmount(150.0);
        newBid.setEmail("invalid-email");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bidService.placeBid(newBid));
        verify(itemRepository).findById("item1");
        verify(bidRepository, never()).save(any(BidEntity.class));
    }

    @Test
    void placeBid_ShouldThrowException_WhenBidTooLow() {
        // Arrange
        when(itemRepository.findById("item1")).thenReturn(Optional.of(activeItem));

        BidEntity highestBid = new BidEntity("item1", "Jane Doe", 200.0, "jane@example.com");
        when(bidRepository.findByItemIdOrderByAmountDesc("item1")).thenReturn(Collections.singletonList(highestBid));

        Bid newBid = new Bid();
        newBid.setItemId("item1");
        newBid.setBidderName("John Doe");
        newBid.setAmount(150.0); // Lower than highest bid
        newBid.setEmail("john@example.com");

        // Act & Assert
        assertThrows(BidException.class, () -> bidService.placeBid(newBid));
        verify(itemRepository).findById("item1");
        verify(bidRepository).findByItemIdOrderByAmountDesc("item1");
        verify(bidRepository, never()).save(any(BidEntity.class));
    }

    @Test
    void placeBid_ShouldThrowException_WhenUserBidLowerThanPrevious() {
        // Arrange
        when(itemRepository.findById("item1")).thenReturn(Optional.of(activeItem));
        when(bidRepository.findByItemIdOrderByAmountDesc("item1")).thenReturn(Collections.emptyList());

        BidEntity previousUserBid = new BidEntity("item1", "John Doe", 200.0, "john@example.com");
        when(bidRepository.findByItemIdAndEmailOrderByAmountDesc("item1", "john@example.com"))
                .thenReturn(Collections.singletonList(previousUserBid));

        Bid newBid = new Bid();
        newBid.setItemId("item1");
        newBid.setBidderName("John Doe");
        newBid.setAmount(150.0); // Lower than user's previous bid
        newBid.setEmail("john@example.com");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bidService.placeBid(newBid));
        verify(itemRepository).findById("item1");
        verify(bidRepository).findByItemIdAndEmailOrderByAmountDesc("item1", "john@example.com");
        verify(bidRepository, never()).save(any(BidEntity.class));
    }

    @Test
    void deleteBid_ShouldDeleteBid_WhenExists() {
        // Arrange
        when(bidRepository.findById("bid1")).thenReturn(Optional.of(bid));

        // Act
        bidService.deleteBid("bid1");

        // Assert
        verify(bidRepository).findById("bid1");
        verify(bidRepository).delete(bid);
    }

    @Test
    void deleteBid_ShouldThrowException_WhenNotExists() {
        // Arrange
        when(bidRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bidService.deleteBid("nonexistent"));
        verify(bidRepository).findById("nonexistent");
        verify(bidRepository, never()).delete(any(BidEntity.class));
    }

    @Test
    void getBidsByEmail_ShouldReturnBidsForEmail() {
        // Arrange
        List<BidEntity> bidEntities = Collections.singletonList(bid);
        when(bidRepository.findByEmail("john@example.com")).thenReturn(bidEntities);
        when(itemRepository.findById(anyString())).thenReturn(Optional.of(activeItem));

        // Act
        List<Bid> result = bidService.getBidsByEmail("john@example.com");

        // Assert
        assertEquals(1, result.size());
        assertEquals("john@example.com", result.get(0).getEmail());
        verify(bidRepository).findByEmail("john@example.com");
    }

    @Test
    void getBidsByEmail_ShouldThrowException_WhenEmailInvalid() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bidService.getBidsByEmail("invalid-email"));
        verify(bidRepository, never()).findByEmail(anyString());
    }
}