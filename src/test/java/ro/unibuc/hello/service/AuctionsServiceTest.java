package ro.unibuc.hello.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
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
import ro.unibuc.hello.dto.BidWithBidder;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.InvalidDataException;

public class AuctionsServiceTest {

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Spy
    @InjectMocks
    private AuctionsService auctionsService = new AuctionsService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuctions() {
        // Arrange
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        ItemEntity item2 = new ItemEntity("22", "Item 2", "description 2", user2);
        List<AuctionEntity> entities = Arrays.asList(
            new AuctionEntity("1", "Auction 1", "Description 1", 100, true, item1, user1),
            new AuctionEntity("2", "Auction 2", "Description 2", 40, false, item2, user2)
        );
        when(auctionRepository.findAll()).thenReturn(entities);

        // Act
        List<AuctionWithAuctioneerAndItem> auctions = auctionsService.getAllAuctions();

        // Assert
        assertEquals(2, auctions.size());
        assertEquals("1", auctions.get(0).getId());
        assertEquals("Auction 1", auctions.get(0).getTitle());
        assertEquals("Description 1", auctions.get(0).getDescription());
        assertEquals(100, auctions.get(0).getStartPrice());
        assertEquals("open", auctions.get(0).getStatus());
        assertEquals("21", auctions.get(0).getItem().getId());
        assertEquals("Item 1", auctions.get(0).getItem().getName());
        assertEquals("description 1", auctions.get(0).getItem().getDescription());
        assertEquals("11", auctions.get(0).getAuctioneer().getId());
        assertEquals("user 1", auctions.get(0).getAuctioneer().getName());

        assertEquals("2", auctions.get(1).getId());
        assertEquals("Auction 2", auctions.get(1).getTitle());
        assertEquals("Description 2", auctions.get(1).getDescription());
        assertEquals(40, auctions.get(1).getStartPrice());
        assertEquals("closed", auctions.get(1).getStatus());
        assertEquals("22", auctions.get(1).getItem().getId());
        assertEquals("Item 2", auctions.get(1).getItem().getName());
        assertEquals("description 2", auctions.get(1).getItem().getDescription());
        assertEquals("12", auctions.get(1).getAuctioneer().getId());
        assertEquals("user 2", auctions.get(1).getAuctioneer().getName());
    }

    @Test
    void testGetAuctionById_ExistingEntity() {
        // Arrange
        String id = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity entity = new AuctionEntity(id, "Auction 1", "Description 1", 100, true, item1, user1);
        when(auctionRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        AuctionWithAuctioneerAndItem auction = auctionsService.getAuctionById(id);

        // Assert
        assertEquals("1", auction.getId());
        assertEquals("Auction 1", auction.getTitle());
        assertEquals("Description 1", auction.getDescription());
        assertEquals(100, auction.getStartPrice());
        assertEquals("open", auction.getStatus());
        assertEquals("21", auction.getItem().getId());
        assertEquals("Item 1", auction.getItem().getName());
        assertEquals("description 1", auction.getItem().getDescription());
        assertEquals("11", auction.getAuctioneer().getId());
        assertEquals("user 1", auction.getAuctioneer().getName());
    }

    @Test
    void testGetAuctionById_NonExistingEntity() {
        // Arrange
        String id = "1";
        when(auctionRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.getAuctionById(id));
    }

    @Test
    void testGetAuctionHighestBid_Success() {
        // Arrange
        String id = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity entity = new AuctionEntity(id, "Auction 1", "Description 1", 100, true, item1, user1);
        when(auctionRepository.findById(id)).thenReturn(Optional.of(entity));

        BidEntity bidEntity = new BidEntity("31", 100, user1, entity);
        doReturn(Optional.of(bidEntity)).when(auctionsService).getAuctionHighestBid(entity);

        // Act
        BidWithBidder bid = auctionsService.getAuctionHighestBid(id);

        // Assert
        assertEquals("31", bid.getId());
        assertEquals(100, bid.getPrice());
        assertEquals("11", bid.getBidder().getId());
        assertEquals("user 1", bid.getBidder().getName());
    }

    @Test
    void testGetAuctionHighestBid_AuctionNotFound() {
        // Arrange
        String id = "1";
        when(auctionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.getAuctionHighestBid(id));
    }

    @Test
    void testGetAuctionHighestBid_NoBidsPlaced() {
        // Arrange
        String id = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity entity = new AuctionEntity(id, "Auction 1", "Description 1", 100, true, item1, user1);
        when(auctionRepository.findById(id)).thenReturn(Optional.of(entity));

        doReturn(Optional.empty()).when(auctionsService).getAuctionHighestBid(entity);

        // Act
        BidWithBidder bid = auctionsService.getAuctionHighestBid(id);

        // Assert
        assertNull(bid);
    }

    @Test
    void testGetAuctionBids_Success() {
        // Arrange
        String id = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity entity = new AuctionEntity(id, "Auction 1", "Description 1", 100, true, item1, user1);
        when(auctionRepository.findById(id)).thenReturn(Optional.of(entity));

        List<BidEntity> bidEntities = Arrays.asList(
            new BidEntity("31", 100, user1, entity),
            new BidEntity("32", 120, user2, entity)
        );
        when(bidRepository.findByAuction(entity)).thenReturn(bidEntities);

        // Act
        List<BidWithBidder> bids = auctionsService.getAuctionBids(id);

        // Assert
        assertEquals(2, bids.size());
        assertEquals("31", bids.get(0).getId());
        assertEquals(100, bids.get(0).getPrice());
        assertEquals("11", bids.get(0).getBidder().getId());
        assertEquals("user 1", bids.get(0).getBidder().getName());
        assertEquals("32", bids.get(1).getId());
        assertEquals(120, bids.get(1).getPrice());
        assertEquals("12", bids.get(1).getBidder().getId());
        assertEquals("user 2", bids.get(1).getBidder().getName());
    }

    @Test
    void testGetAuctionBids_AuctionNotFound() {
        // Arrange
        String id = "1";
        when(auctionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.getAuctionBids(id));
    }

    @Test
    void testSaveAuction_Success() {
        // Arrange
        String auctioneerId = "11";
        AuctionPost auction = new AuctionPost("Auction 1", "Description 1", 100, "21");
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity("1", "Auction 1", "Description 1", 100, true, item1, user1);
        
        when(userRepository.findById(auctioneerId)).thenReturn(Optional.of(user1));
        when(itemRepository.findById(auction.getItemId())).thenReturn(Optional.of(item1));
        when(auctionRepository.findByItem(item1)).thenReturn(Arrays.asList());
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);

        // Act
        AuctionWithAuctioneerAndItem savedAuction = auctionsService.saveAuction(auctioneerId, auction);

        // Assert
        assertEquals("1", savedAuction.getId());
        assertEquals("Auction 1", savedAuction.getTitle());
        assertEquals("Description 1", savedAuction.getDescription());
        assertEquals(100, savedAuction.getStartPrice());
        assertEquals("open", savedAuction.getStatus());
        assertEquals("21", savedAuction.getItem().getId());
        assertEquals("Item 1", savedAuction.getItem().getName());
        assertEquals("description 1", savedAuction.getItem().getDescription());
        assertEquals("11", savedAuction.getAuctioneer().getId());
        assertEquals("user 1", savedAuction.getAuctioneer().getName());        
    }

    @Test
    void testSaveAuction_AuctioneerNotFound() {
        // Arrange
        String auctioneerId = "11";
        AuctionPost auction = new AuctionPost("Auction 1", "Description 1", 100, "21");
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity("1", "Auction 1", "Description 1", 100, true, item1, user1);
        List<AuctionEntity> itemAuctions = Arrays.asList(
            new AuctionEntity("2", "Auction 2", "Description 2", 100, false, item1, user1),
            new AuctionEntity("3", "Auction 3", "Description 3", 100, false, item1, user1)
        );
        
        when(userRepository.findById(auctioneerId)).thenReturn(Optional.empty());
        when(itemRepository.findById(auction.getItemId())).thenReturn(Optional.of(item1));
        when(auctionRepository.findByItem(item1)).thenReturn(itemAuctions);
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.saveAuction(auctioneerId, auction));
    }

    @Test
    void testSaveAuction_ItemNotFound() {
        // Arrange
        String auctioneerId = "11";
        AuctionPost auction = new AuctionPost("Auction 1", "Description 1", 100, "21");
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity("1", "Auction 1", "Description 1", 100, true, item1, user1);
        
        when(userRepository.findById(auctioneerId)).thenReturn(Optional.of(user1));
        when(itemRepository.findById(auction.getItemId())).thenReturn(Optional.empty());
        when(auctionRepository.findByItem(item1)).thenReturn(Arrays.asList());
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.saveAuction(auctioneerId, auction));
    }

    @Test
    void testSaveAuction_ItemNotOwnedByAuctioneer() {
        // Arrange
        String auctioneerId = "11";
        AuctionPost auction = new AuctionPost("Auction 1", "Description 1", 100, "21");
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user2);
        AuctionEntity auctionEntity = new AuctionEntity("1", "Auction 1", "Description 1", 100, true, item1, user1);
        
        when(userRepository.findById(auctioneerId)).thenReturn(Optional.of(user1));
        when(itemRepository.findById(auction.getItemId())).thenReturn(Optional.of(item1));
        when(auctionRepository.findByItem(item1)).thenReturn(Arrays.asList());
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.saveAuction(auctioneerId, auction));
    }

    @Test
    void testSaveAuction_ItemAlreadyInOpenAuction() {
        // Arrange
        String auctioneerId = "11";
        AuctionPost auction = new AuctionPost("Auction 1", "Description 1", 100, "21");
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity("1", "Auction 1", "Description 1", 100, true, item1, user1);
        List<AuctionEntity> itemAuctions = Arrays.asList(
            new AuctionEntity("2", "Auction 2", "Description 2", 100, false, item1, user1),
            new AuctionEntity("3", "Auction 3", "Description 3", 100, true, item1, user1)
        );
        
        when(userRepository.findById(auctioneerId)).thenReturn(Optional.of(user1));
        when(itemRepository.findById(auction.getItemId())).thenReturn(Optional.of(item1));
        when(auctionRepository.findByItem(item1)).thenReturn(itemAuctions);
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(auctionEntity);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.saveAuction(auctioneerId, auction));
    }

    @Test
    void testUpdateAuction_Success() {
        // Arrange
        String id = "1";
        AuctionPut auction = new AuctionPut("Auction updated", "Description updated");
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity entity = new AuctionEntity(id, "Auction updated", "Description updated", 100, true, item1, user1);
        when(auctionRepository.findById(id)).thenReturn(Optional.of(entity));
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(entity);

        // Act
        AuctionWithAuctioneerAndItem updatedAuction = auctionsService.updateAuction(id, auction);

        // Assert
        assertEquals("1", updatedAuction.getId());
        assertEquals("Auction updated", updatedAuction.getTitle());
        assertEquals("Description updated", updatedAuction.getDescription());
        assertEquals(100, updatedAuction.getStartPrice());
        assertEquals("open", updatedAuction.getStatus());
        assertEquals("21", updatedAuction.getItem().getId());
        assertEquals("Item 1", updatedAuction.getItem().getName());
        assertEquals("description 1", updatedAuction.getItem().getDescription());
        assertEquals("11", updatedAuction.getAuctioneer().getId());
        assertEquals("user 1", updatedAuction.getAuctioneer().getName());
    }

    @Test
    void testUpdateAuction_AuctionNotFound() {
        // Arrange
        String id = "1";
        AuctionPut auction = new AuctionPut("Auction updated", "Description updated");
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity entity = new AuctionEntity(id, "Auction updated", "Description updated", 100, true, item1, user1);
        when(auctionRepository.findById(id)).thenReturn(Optional.empty());
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(entity);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.updateAuction(id, auction));
    }

    @Test
    void testPlaceBid_Success_ExistingHighestBid() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(20);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);
        BidEntity createdBidEntity = new BidEntity("32", 20, user2, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(bidRepository.save(any(BidEntity.class))).thenReturn(createdBidEntity);

        // Act
        BidWithBidder createdBid = auctionsService.placeBid(auctionId, userId, placeBid);

        // Assert
        assertEquals("32", createdBid.getId());
        assertEquals(20, createdBid.getPrice());
        assertEquals("12", createdBid.getBidder().getId());
        assertEquals("user 2", createdBid.getBidder().getName());
    }

    @Test
    void testPlaceBid_Success_FirstBid() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(20);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity createdBidEntity = new BidEntity("32", 20, user2, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        doReturn(Optional.empty()).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(bidRepository.save(any(BidEntity.class))).thenReturn(createdBidEntity);

        // Act
        BidWithBidder createdBid = auctionsService.placeBid(auctionId, userId, placeBid);

        // Assert
        assertEquals("32", createdBid.getId());
        assertEquals(20, createdBid.getPrice());
        assertEquals("12", createdBid.getBidder().getId());
        assertEquals("user 2", createdBid.getBidder().getName());
    }

    @Test
    void testPlaceBid_Success_FirstBidEqualToStartPrice() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(5);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity createdBidEntity = new BidEntity("32", 5, user2, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        doReturn(Optional.empty()).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(bidRepository.save(any(BidEntity.class))).thenReturn(createdBidEntity);

        // Act
        BidWithBidder createdBid = auctionsService.placeBid(auctionId, userId, placeBid);

        // Assert
        assertEquals("32", createdBid.getId());
        assertEquals(5, createdBid.getPrice());
        assertEquals("12", createdBid.getBidder().getId());
        assertEquals("user 2", createdBid.getBidder().getName());
    }

    @Test
    void testPlaceBid_AuctionNotFound() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(20);
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.placeBid(auctionId, userId, placeBid));
    }

    @Test
    void testPlaceBid_UserNotFound() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(20);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.placeBid(auctionId, userId, placeBid));
    }

    @Test
    void testPlaceBid_ClosedAuction() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(20);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, false, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);
        BidEntity createdBidEntity = new BidEntity("32", 20, user2, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(bidRepository.save(any(BidEntity.class))).thenReturn(createdBidEntity);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.placeBid(auctionId, userId, placeBid));
    }

    @Test
    void testPlaceBid_BidderSameAsAuctioneer() {
        // Arrange
        String auctionId = "1";
        String userId = "11";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(20);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);
        BidEntity createdBidEntity = new BidEntity("32", 20, user2, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(bidRepository.save(any(BidEntity.class))).thenReturn(createdBidEntity);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.placeBid(auctionId, userId, placeBid));
    }

    @Test
    void testPlaceBid_BidEqualToExistingHighestBid() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(10);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);
        BidEntity createdBidEntity = new BidEntity("32", 10, user2, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(bidRepository.save(any(BidEntity.class))).thenReturn(createdBidEntity);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.placeBid(auctionId, userId, placeBid));
    }

    @Test
    void testPlaceBid_BidLowerThanExistingHighestBid() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(5);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);
        BidEntity createdBidEntity = new BidEntity("32", 5, user2, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(bidRepository.save(any(BidEntity.class))).thenReturn(createdBidEntity);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.placeBid(auctionId, userId, placeBid));
    }
    
    @Test
    void testPlaceBid_FirstBidLowerThanStartPrice() {
        // Arrange
        String auctionId = "1";
        String userId = "12";
        AuctionPlaceBidRequest placeBid = new AuctionPlaceBidRequest(4);
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity createdBidEntity = new BidEntity("32", 4, user2, auctionEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        doReturn(Optional.empty()).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(bidRepository.save(any(BidEntity.class))).thenReturn(createdBidEntity);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.placeBid(auctionId, userId, placeBid));
    }

    @Test
    void testCloseAuction_Success() {
        // Arrange
        String auctionId = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);
        ItemEntity updatedItem = new ItemEntity("21", "Item 1", "description 1", user2);
        AuctionEntity updatedAuction = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, false, item1, user1);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(updatedItem);
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(updatedAuction);

        // Act
        auctionsService.closeAuction(auctionId);

        // Assert
        verify(itemRepository, times(1)).save(item1);
        verify(auctionRepository, times(1)).save(auctionEntity);
    }

    @Test
    void testCloseAuction_AuctionNotFound() {
        // Arrange
        String auctionId = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);
        ItemEntity updatedItem = new ItemEntity("21", "Item 1", "description 1", user2);
        AuctionEntity updatedAuction = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, false, item1, user1);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(updatedItem);
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(updatedAuction);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.closeAuction(auctionId));
    }

    @Test
    void testCloseAuction_AuctionAlreadyClosed() {
        // Arrange
        String auctionId = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, false, item1, user1);
        BidEntity highestBid = new BidEntity("31", 10, user1, auctionEntity);
        ItemEntity updatedItem = new ItemEntity("21", "Item 1", "description 1", user2);
        AuctionEntity updatedAuction = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, false, item1, user1);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        doReturn(Optional.of(highestBid)).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(updatedItem);
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(updatedAuction);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.closeAuction(auctionId));
    }

    @Test
    void testCloseAuction_NoBids() {
        // Arrange
        String auctionId = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, true, item1, user1);
        ItemEntity updatedItem = new ItemEntity("21", "Item 1", "description 1", user2);
        AuctionEntity updatedAuction = new AuctionEntity(auctionId, "Auction 1", "Description 1", 5, false, item1, user1);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));
        doReturn(Optional.empty()).when(auctionsService).getAuctionHighestBid(auctionEntity);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(updatedItem);
        when(auctionRepository.save(any(AuctionEntity.class))).thenReturn(updatedAuction);

        // Act & Assert
        assertThrows(InvalidDataException.class, () -> auctionsService.closeAuction(auctionId));
    }

    @Test
    void testDeleteAuction_Success() {
        // Arrange
        String id = "1";
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auctionEntity = new AuctionEntity(id, "Auction 1", "Description 1", 5, true, item1, user1);
        
        when(auctionRepository.findById(id)).thenReturn(Optional.of(auctionEntity));

        // Act
        auctionsService.deleteAuction(id);

        // Assert
        verify(auctionRepository, times(1)).delete(auctionEntity);
    }

    @Test
    void testDeleteAuction_AuctionNotFound() {
        // Arrange
        String id = "1";
        
        when(auctionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> auctionsService.deleteAuction(id));
    }

    @Test
    void testGetAuctionHighestBid_ExistingBids() {
        // Arrange
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        UserEntity user2 = new UserEntity("12", "user 2", "username2", "password2");
        UserEntity user3 = new UserEntity("13", "user 3", "username3", "password3");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity entity = new AuctionEntity("1", "Auction 1", "Description 1", 5, true, item1, user1);

        BidEntity bid1 = new BidEntity("31", 100, user2, entity);
        BidEntity bid2 = new BidEntity("32", 130, user2, entity);
        BidEntity bid3 = new BidEntity("32", 110, user3, entity);
        List<BidEntity> bidEntities = Arrays.asList(bid1, bid2, bid3);

        when(bidRepository.findByAuction(entity)).thenReturn(bidEntities);

        // Act
        Optional<BidEntity> highestBid = auctionsService.getAuctionHighestBid(entity);

        // Assert
        assertEquals(true, highestBid.isPresent());
        assertEquals(bid2.getId(), highestBid.get().getId());
        assertEquals(bid2.getPrice(), highestBid.get().getPrice());
        assertEquals(bid2.getBidder().getId(), highestBid.get().getBidder().getId());
        assertEquals(bid2.getAuction().getId(), highestBid.get().getAuction().getId());
    }

    @Test
    void testGetAuctionHighestBid_NoBids() {
        // Arrange
        UserEntity user1 = new UserEntity("11", "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity entity = new AuctionEntity("1", "Auction 1", "Description 1", 5, true, item1, user1);

        when(bidRepository.findByAuction(entity)).thenReturn(Arrays.asList());

        // Act
        Optional<BidEntity> highestBid = auctionsService.getAuctionHighestBid(entity);

        // Assert
        assertEquals(false, highestBid.isPresent());
    }
}
