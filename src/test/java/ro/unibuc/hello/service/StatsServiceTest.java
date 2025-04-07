package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.AuctionStats;
import ro.unibuc.hello.dto.ItemPopularity;
import ro.unibuc.hello.dto.UserStats;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BidRepository bidRepository;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private StatsService statsService;

    private ItemEntity testItem1;
    private ItemEntity testItem2;
    private BidEntity testBid1;
    private BidEntity testBid2;
    private BidEntity testBid3;

    @BeforeEach
    void setUp() {
        // Set up test data
        testItem1 = new ItemEntity("Test Item 1", "Description 1", 100.0, LocalDateTime.now().plusDays(1), "creator1@example.com", Category.ELECTRONICS);
        testItem1.setId("item1");
        testItem1.setActive(true);
        testItem1.setCreatedAt(LocalDateTime.now().minusDays(5));

        testItem2 = new ItemEntity("Test Item 2", "Description 2", 200.0, LocalDateTime.now().minusDays(1), "creator2@example.com", Category.FASHION);
        testItem2.setId("item2");
        testItem2.setActive(false);
        testItem2.setCreatedAt(LocalDateTime.now().minusDays(10));

        testBid1 = new BidEntity("item1", "Bidder 1", 150.0, "bidder1@example.com");
        testBid1.setId("bid1");
        testBid1.setCreatedAt(LocalDateTime.now().minusDays(3));

        testBid2 = new BidEntity("item1", "Bidder 2", 200.0, "bidder2@example.com");
        testBid2.setId("bid2");
        testBid2.setCreatedAt(LocalDateTime.now().minusDays(2));

        testBid3 = new BidEntity("item2", "Bidder 1", 250.0, "bidder1@example.com");
        testBid3.setId("bid3");
        testBid3.setCreatedAt(LocalDateTime.now().minusDays(1));
    }

    @Test
    void getOverallStats_ShouldReturnCompleteStats() {
        // Arrange
        List<ItemEntity> items = Arrays.asList(testItem1, testItem2);
        List<BidEntity> bids = Arrays.asList(testBid1, testBid2, testBid3);

        when(itemRepository.findAll()).thenReturn(items);
        when(bidRepository.findAll()).thenReturn(bids);
        when(bidRepository.findByItemId("item1")).thenReturn(Arrays.asList(testBid1, testBid2));
        when(bidRepository.findByItemId("item2")).thenReturn(Collections.singletonList(testBid3));

        // Act
        AuctionStats stats = statsService.getOverallStats();

        // Assert
        assertNotNull(stats);
        assertEquals(2, stats.getTotalItems());
        assertEquals(1, stats.getActiveItems());
        assertEquals(1, stats.getCompletedAuctions());
        assertEquals(3, stats.getTotalBids());
        assertEquals(1.5, stats.getAverageBids());
        assertEquals(250.0, stats.getHighestBidAmount());

        // Verify category counters
        Map<String, Integer> itemsByCategory = stats.getItemsByCategory();
        assertNotNull(itemsByCategory);
        assertEquals(1, itemsByCategory.get(Category.ELECTRONICS.name()));
        assertEquals(1, itemsByCategory.get(Category.FASHION.name()));

        // Verify unique bidders
        assertEquals(2, stats.getUniqueBidders());

        // Verify category popularity
        Map<String, Double> categoryPopularity = stats.getCategoryPopularity();
        assertNotNull(categoryPopularity);
        assertTrue(categoryPopularity.containsKey(Category.ELECTRONICS.name()));
        assertTrue(categoryPopularity.containsKey(Category.FASHION.name()));

        // Verify top bidders
        Map<String, Integer> topBidders = stats.getTopBidders();
        assertNotNull(topBidders);
        assertEquals(2, topBidders.size());
    }

    @Test
    void getOverallStats_WithEmptyRepositories_ShouldReturnEmptyStats() {
        // Arrange
        when(itemRepository.findAll()).thenReturn(Collections.emptyList());
        when(bidRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        AuctionStats stats = statsService.getOverallStats();

        // Assert
        assertNotNull(stats);
        assertEquals(0, stats.getTotalItems());
        assertEquals(0, stats.getActiveItems());
        assertEquals(0, stats.getCompletedAuctions());
        assertEquals(0, stats.getTotalBids());
        assertEquals(0, stats.getAverageBids());
        assertEquals(0, stats.getHighestBidAmount());
        assertEquals(0, stats.getUniqueBidders());
        assertEquals(0, stats.getBidsPerDay());
    }

    @Test
    void getOverallStats_WithSameDayBids_ShouldHandleBidsPerDay() {
        // Arrange
        List<ItemEntity> items = Collections.singletonList(testItem1);

        // Create two bids on the same day
        BidEntity sameDayBid1 = new BidEntity("item1", "Bidder 1", 150.0, "bidder1@example.com");
        sameDayBid1.setId("bid1");
        LocalDateTime sameBidTime = LocalDateTime.now().minusDays(1);
        sameDayBid1.setCreatedAt(sameBidTime);

        BidEntity sameDayBid2 = new BidEntity("item1", "Bidder 2", 200.0, "bidder2@example.com");
        sameDayBid2.setId("bid2");
        sameDayBid2.setCreatedAt(sameBidTime.plusHours(2)); // Same day, different hour

        List<BidEntity> bids = Arrays.asList(sameDayBid1, sameDayBid2);

        when(itemRepository.findAll()).thenReturn(items);
        when(bidRepository.findAll()).thenReturn(bids);
        when(bidRepository.findByItemId(anyString())).thenReturn(bids);

        // Act
        AuctionStats stats = statsService.getOverallStats();

        // Assert
        assertEquals(2, stats.getTotalBids());
        assertEquals(2.0, stats.getBidsPerDay()); // 2 bids in 1 day = 2 bids per day
    }



    @Test
    void getUserStats_WithInvalidEmail_ShouldThrowException() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> statsService.getUserStats("invalid-email"));
    }

    @Test
    void getUserStats_WithNoUserBids_ShouldReturnEmptyStats() {
        // Arrange
        String email = "nobids@example.com";

        when(itemRepository.findAll()).thenReturn(Collections.emptyList());
        when(bidRepository.findByEmail(email)).thenReturn(Collections.emptyList());

        // Act
        UserStats stats = statsService.getUserStats(email);

        // Assert
        assertNotNull(stats);
        assertEquals(email, stats.getEmail());
        assertEquals(0, stats.getTotalBidsMade());
        assertEquals(0, stats.getBidsWon());
        assertEquals(0, stats.getTotalSpent());
        assertEquals(0, stats.getTotalEarned());
        assertEquals(0, stats.getBiddingFrequency());
        assertEquals(0, stats.getUniqueItemsBidOn());
    }

    @Test
    void getUserStats_WithConsecutiveDayBids_ShouldCalculateBidStreak() {
        // Arrange
        String email = "streak@example.com";

        // Create bids on consecutive days
        BidEntity dayOneBid = new BidEntity("item1", "Streak User", 150.0, email);
        dayOneBid.setId("bid1");
        LocalDateTime baseTime = LocalDateTime.now().minusDays(3);
        dayOneBid.setCreatedAt(baseTime);

        BidEntity dayTwoBid = new BidEntity("item1", "Streak User", 170.0, email);
        dayTwoBid.setId("bid2");
        dayTwoBid.setCreatedAt(baseTime.plusDays(1)); // Next day

        BidEntity dayThreeBid = new BidEntity("item2", "Streak User", 200.0, email);
        dayThreeBid.setId("bid3");
        dayThreeBid.setCreatedAt(baseTime.plusDays(2)); // Next day

        List<BidEntity> userBids = Arrays.asList(dayOneBid, dayTwoBid, dayThreeBid);

        when(itemRepository.findAll()).thenReturn(Collections.emptyList());
        when(bidRepository.findByEmail(email)).thenReturn(userBids);

        // Act
        UserStats stats = statsService.getUserStats(email);

        // Assert
        assertNotNull(stats);
        assertEquals(3, stats.getTotalBidsMade());
        assertEquals(3, stats.getBidStreak()); // 3 consecutive days
    }

    @Test
    void getUserStats_WithUserCreatedItems_ShouldCalculateEarnings() {
        // Arrange
        String email = "creator@example.com";

        // User created this item
        ItemEntity userItem = new ItemEntity("User Item", "Description", 100.0, LocalDateTime.now().minusDays(5), email, Category.ELECTRONICS);
        userItem.setId("userItem");
        userItem.setActive(false); // Completed auction

        // Highest bid on user's item
        BidEntity highestBid = new BidEntity("userItem", "Bidder", 250.0, "bidder@example.com");
        highestBid.setId("highBid");

        when(itemRepository.findAll()).thenReturn(Collections.singletonList(userItem));
        when(bidRepository.findByEmail(email)).thenReturn(Collections.emptyList());
        when(bidRepository.findByItemIdOrderByAmountDesc("userItem")).thenReturn(Collections.singletonList(highestBid));

        // Act
        UserStats stats = statsService.getUserStats(email);

        // Assert
        assertNotNull(stats);
        assertEquals(1, stats.getTotalItemsListed());
        assertEquals(0, stats.getActiveItemsListed());
        assertEquals(1, stats.getCompletedAuctions());
        assertEquals(250.0, stats.getTotalEarned()); // Earned from highest bid
    }

    @Test
    void getItemPopularity_WithBids_ShouldReturnCompleteStats() {
        // Arrange
        String itemId = "item1";
        List<BidEntity> itemBids = Arrays.asList(testBid1, testBid2);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(testItem1));
        when(bidRepository.findByItemId(itemId)).thenReturn(itemBids);

        // Act
        ItemPopularity popularity = statsService.getItemPopularity(itemId);

        // Assert
        assertNotNull(popularity);
        assertEquals(itemId, popularity.getItemId());
        assertEquals("Test Item 1", popularity.getItemName());
        assertEquals(2, popularity.getTotalBids());
        assertEquals(2, popularity.getUniqueBidders());

        // Check bid frequency (bids per day)
        assertTrue(popularity.getBidFrequency() > 0);

        // Check bid increase rate
        double bidIncreaseRate = popularity.getBidIncreaseRate();
        assertEquals(33.33, bidIncreaseRate, 0.1); // (200-150)/150 * 100 = ~33.33%

        // Check price increase from initial
        double priceIncrease = popularity.getPriceIncrease();
        assertEquals(100.0, priceIncrease, 0.1); // (200-100)/100 * 100 = 100%

        // Check popularity score
        assertTrue(popularity.getPopularityScore() > 0);
    }

    @Test
    void getItemPopularity_WithNonexistentItem_ShouldThrowException() {
        // Arrange
        String itemId = "nonexistent";
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> statsService.getItemPopularity(itemId));
    }

    @Test
    void getItemPopularity_WithNoItemBids_ShouldReturnLowPopularity() {
        // Arrange
        String itemId = "itemWithNoBids";
        ItemEntity itemWithNoBids = new ItemEntity("No Bids Item", "Description", 100.0, LocalDateTime.now().plusDays(1), "creator@example.com", Category.ELECTRONICS);
        itemWithNoBids.setId(itemId);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(itemWithNoBids));
        when(bidRepository.findByItemId(itemId)).thenReturn(Collections.emptyList());

        // Act
        ItemPopularity popularity = statsService.getItemPopularity(itemId);

        // Assert
        assertNotNull(popularity);
        assertEquals(itemId, popularity.getItemId());
        assertEquals("No Bids Item", popularity.getItemName());
        assertEquals(0, popularity.getTotalBids());
        assertEquals(0, popularity.getUniqueBidders());
        assertEquals(0, popularity.getBidFrequency());
        assertEquals(0, popularity.getBidIncreaseRate());
        assertFalse(popularity.isHot());
        assertEquals(0, popularity.getPriceIncrease());
    }

    @Test
    void getItemPopularity_WithSingleBid_ShouldCalculateCorrectly() {
        // Arrange
        String itemId = "singleBidItem";
        ItemEntity item = new ItemEntity("Single Bid Item", "Description", 100.0, LocalDateTime.now().plusDays(1), "creator@example.com", Category.ELECTRONICS);
        item.setId(itemId);

        BidEntity singleBid = new BidEntity(itemId, "Bidder", 150.0, "bidder@example.com");
        singleBid.setId("singleBid");
        singleBid.setCreatedAt(LocalDateTime.now().minusHours(1));

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(bidRepository.findByItemId(itemId)).thenReturn(Collections.singletonList(singleBid));

        // Act
        ItemPopularity popularity = statsService.getItemPopularity(itemId);

        // Assert
        assertNotNull(popularity);
        assertEquals(1, popularity.getTotalBids());
        assertEquals(1, popularity.getUniqueBidders());
        assertEquals(0, popularity.getBidIncreaseRate()); // Can't calculate with just one bid
        assertEquals(50.0, popularity.getPriceIncrease(), 0.1); // (150-100)/100 * 100 = 50%
    }

    @Test
    void getItemPopularity_WithRecentBids_ShouldHaveHigherRecencyScore() {
        // Arrange
        String itemId = "recentBidItem";
        ItemEntity item = new ItemEntity("Recent Bid Item", "Description", 100.0, LocalDateTime.now().plusDays(1), "creator@example.com", Category.ELECTRONICS);
        item.setId(itemId);

        BidEntity recentBid = new BidEntity(itemId, "Bidder", 150.0, "bidder@example.com");
        recentBid.setId("recentBid");
        recentBid.setCreatedAt(LocalDateTime.now().minusHours(1)); // Very recent bid

        BidEntity olderBid = new BidEntity(itemId, "Bidder", 120.0, "bidder@example.com");
        olderBid.setId("olderBid");
        olderBid.setCreatedAt(LocalDateTime.now().minusDays(5)); // Older bid

        // First test with recent bid only
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(bidRepository.findByItemId(itemId)).thenReturn(Collections.singletonList(recentBid));

        // Act
        ItemPopularity recentPopularity = statsService.getItemPopularity(itemId);

        // Now test with older bid
        when(bidRepository.findByItemId(itemId)).thenReturn(Collections.singletonList(olderBid));

        // Act again
        ItemPopularity olderPopularity = statsService.getItemPopularity(itemId);

        // Assert
        assertTrue(recentPopularity.getPopularityScore() > olderPopularity.getPopularityScore(),
                "Recent bid should result in higher popularity score");
    }

    @Test
    void getCategoryStats_WithValidCategory_ShouldReturnCompleteStats() {
        // Arrange
        String category = "ELECTRONICS";

        List<ItemEntity> allItems = Collections.singletonList(testItem1);
        List<BidEntity> itemBids = Arrays.asList(testBid1, testBid2);

        when(itemRepository.findAll()).thenReturn(allItems);
        when(bidRepository.findByItemId("item1")).thenReturn(itemBids);

        // Act
        AuctionStats stats = statsService.getCategoryStats(category);

        // Assert
        assertNotNull(stats);
        assertEquals(1, stats.getTotalItems());
        assertEquals(1, stats.getActiveItems());
        assertEquals(0, stats.getCompletedAuctions());
        assertEquals(2, stats.getTotalBids());
        assertEquals(2.0, stats.getAverageBids());
        assertEquals(200.0, stats.getHighestBidAmount());
        assertEquals(2, stats.getUniqueBidders());

        // Verify bidding frequency
        assertTrue(stats.getBidsPerDay() > 0);

        // Verify day of week distribution
        Map<String, Integer> bidsByDay = stats.getBidsByDay();
        assertNotNull(bidsByDay);
    }

    @Test
    void getCategoryStats_WithInvalidCategory_ShouldThrowException() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> statsService.getCategoryStats("INVALID"));
    }

    @Test
    void getCategoryStats_WithNoCategoryItems_ShouldReturnEmptyStats() {
        // Arrange
        String category = "BOOKS"; // No items in this category

        when(itemRepository.findAll()).thenReturn(Arrays.asList(testItem1, testItem2)); // Only ELECTRONICS and FASHION

        // Act
        AuctionStats stats = statsService.getCategoryStats(category);

        // Assert
        assertNotNull(stats);
        assertEquals(0, stats.getTotalItems());
        assertEquals(0, stats.getActiveItems());
        assertEquals(0, stats.getCompletedAuctions());
        assertEquals(0, stats.getTotalBids());
    }

    @Test
    void getPopularItems_WithActiveItems_ShouldReturnSortedItems() {
        // Arrange
        int limit = 2;

        ItemEntity item1 = new ItemEntity("Popular Item 1", "Description", 100.0, LocalDateTime.now().plusDays(1), "creator@example.com", Category.ELECTRONICS);
        item1.setId("popular1");
        item1.setActive(true);

        ItemEntity item2 = new ItemEntity("Popular Item 2", "Description", 100.0, LocalDateTime.now().plusDays(1), "creator@example.com", Category.BOOKS);
        item2.setId("popular2");
        item2.setActive(true);

        List<ItemEntity> activeItems = Arrays.asList(item1, item2);

        // Create mock bids to establish popularity
        BidEntity bid1 = new BidEntity("popular1", "Bidder1", 150.0, "bidder1@example.com");
        BidEntity bid2 = new BidEntity("popular1", "Bidder2", 200.0, "bidder2@example.com");
        BidEntity bid3 = new BidEntity("popular2", "Bidder1", 150.0, "bidder1@example.com");

        when(itemRepository.findByActive(true)).thenReturn(activeItems);
        when(itemRepository.findById("popular1")).thenReturn(Optional.of(item1));
        when(itemRepository.findById("popular2")).thenReturn(Optional.of(item2));
        when(bidRepository.findByItemId("popular1")).thenReturn(Arrays.asList(bid1, bid2));
        when(bidRepository.findByItemId("popular2")).thenReturn(Collections.singletonList(bid3));

        // Act
        List<ItemPopularity> popularItems = statsService.getPopularItems(limit);

        // Assert
        assertNotNull(popularItems);
        assertEquals(2, popularItems.size());
        // The first item should be more popular (more bids)
        assertEquals("popular1", popularItems.get(0).getItemId());
        assertEquals("popular2", popularItems.get(1).getItemId());
    }

    @Test
    void getPopularItems_WithNoActiveItems_ShouldReturnEmptyList() {
        // Arrange
        when(itemRepository.findByActive(true)).thenReturn(Collections.emptyList());

        // Act
        List<ItemPopularity> popularItems = statsService.getPopularItems(5);

        // Assert
        assertNotNull(popularItems);
        assertTrue(popularItems.isEmpty());
    }

    @Test
    void getPopularCategories_ShouldReturnSortedCategories() {
        // Arrange - Mock the getOverallStats method using spy
        StatsService spyService = spy(statsService);

        AuctionStats mockStats = new AuctionStats();
        Map<String, Double> categoryPopularity = new HashMap<>();
        categoryPopularity.put("ELECTRONICS", 5.0);
        categoryPopularity.put("BOOKS", 3.0);
        categoryPopularity.put("FASHION", 1.0);
        mockStats.setCategoryPopularity(categoryPopularity);

        doReturn(mockStats).when(spyService).getOverallStats();

        // Act
        Map<String, Double> result = spyService.getPopularCategories();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verify sorting - should be in descending order of popularity
        Iterator<Map.Entry<String, Double>> iterator = result.entrySet().iterator();
        assertEquals("ELECTRONICS", iterator.next().getKey());
        assertEquals("BOOKS", iterator.next().getKey());
        assertEquals("FASHION", iterator.next().getKey());
    }

    @Test
    void getMostActiveUsers_WithValidUsers_ShouldReturnSortedUsers() {
        // Arrange - We need to spy the service to mock getUserStats
        StatsService spyService = spy(statsService);

        String email1 = "active1@example.com";
        String email2 = "active2@example.com";

        // Set up bids with different users
        BidEntity bid1 = new BidEntity("item1", "Active User 1", 150.0, email1);
        BidEntity bid2 = new BidEntity("item1", "Active User 1", 200.0, email1);
        BidEntity bid3 = new BidEntity("item2", "Active User 2", 150.0, email2);

        List<BidEntity> allBids = Arrays.asList(bid1, bid2, bid3);

        // Create user stats with different activity scores
        UserStats stats1 = new UserStats();
        stats1.setEmail(email1);
        stats1.setUserActivityScore(80.0);

        UserStats stats2 = new UserStats();
        stats2.setEmail(email2);
        stats2.setUserActivityScore(50.0);

        when(bidRepository.findAll()).thenReturn(allBids);
        doReturn(stats1).when(spyService).getUserStats(email1);
        doReturn(stats2).when(spyService).getUserStats(email2);

        // Act
        List<UserStats> activeUsers = spyService.getMostActiveUsers(2);

        // Assert
        assertNotNull(activeUsers);
        assertEquals(2, activeUsers.size());
        // Should be sorted by activity score in descending order
        assertEquals(email1, activeUsers.get(0).getEmail());
        assertEquals(email2, activeUsers.get(1).getEmail());
    }

    @Test
    void getMostActiveUsers_WithInvalidEmails_ShouldSkipInvalidUsers() {
        // Arrange - We need to spy the service to mock getUserStats
        StatsService spyService = spy(statsService);

        String validEmail = "valid@example.com";
        String invalidEmail = "invalid-email";

        BidEntity validBid = new BidEntity("item1", "Valid User", 150.0, validEmail);
        BidEntity invalidBid = new BidEntity("item1", "Invalid User", 200.0, invalidEmail);

        List<BidEntity> allBids = Arrays.asList(validBid, invalidBid);

        UserStats validUserStats = new UserStats();
        validUserStats.setEmail(validEmail);
        validUserStats.setUserActivityScore(75.0);

        when(bidRepository.findAll()).thenReturn(allBids);
        doReturn(validUserStats).when(spyService).getUserStats(validEmail);
        doThrow(new IllegalArgumentException("Invalid email")).when(spyService).getUserStats(invalidEmail);

        // Act
        List<UserStats> activeUsers = spyService.getMostActiveUsers(5);

        // Assert
        assertNotNull(activeUsers);
        assertEquals(1, activeUsers.size()); // Only the valid user should be included
        assertEquals(validEmail, activeUsers.get(0).getEmail());
    }

    @Test
    void getBiddingHourDistribution_ShouldReturnAllHours() {
        // Arrange
        List<BidEntity> allBids = Arrays.asList(testBid1, testBid2);
        when(bidRepository.findAll()).thenReturn(allBids);

        // Act
        Map<String, Integer> distribution = statsService.getBiddingHourDistribution();

        // Assert
        assertNotNull(distribution);
        assertEquals(24, distribution.size()); // Hours 0-23 should all be present

        // The hours corresponding to our test bids should have counts
        int hour1 = testBid1.getCreatedAt().getHour();
        int hour2 = testBid2.getCreatedAt().getHour();

        if (hour1 == hour2) {
            assertEquals(2, distribution.get(String.valueOf(hour1)));
        } else {
            assertEquals(1, distribution.get(String.valueOf(hour1)));
            assertEquals(1, distribution.get(String.valueOf(hour2)));
        }

        // Check that all other hours have 0 counts
        for (int i = 0; i < 24; i++) {
            String hour = String.valueOf(i);
            if (i != hour1 && i != hour2) {
                assertEquals(0, distribution.get(hour));
            }
        }
    }

    @Test
    void getHotItems_ShouldReturnOnlyHotItems() {
        // Arrange - We need to spy the service to mock getItemPopularity
        StatsService spyService = spy(statsService);

        ItemEntity item1 = new ItemEntity("Hot Item", "Description", 100.0, LocalDateTime.now().plusDays(1), "creator@example.com", Category.ELECTRONICS);
        item1.setId("hot1");
        item1.setActive(true);

        ItemEntity item2 = new ItemEntity("Cold Item", "Description", 100.0, LocalDateTime.now().plusDays(1), "creator@example.com", Category.BOOKS);
        item2.setId("cold1");
        item2.setActive(true);

        List<ItemEntity> activeItems = Arrays.asList(item1, item2);

        // Create popularity objects - one hot, one not
        ItemPopularity hotPopularity = new ItemPopularity();
        hotPopularity.setItemId("hot1");
        hotPopularity.setItemName("Hot Item");
        hotPopularity.setHot(true);
        hotPopularity.setBidFrequency(10.0); // Above threshold

        ItemPopularity coldPopularity = new ItemPopularity();
        coldPopularity.setItemId("cold1");
        coldPopularity.setItemName("Cold Item");
        coldPopularity.setHot(false);
        coldPopularity.setBidFrequency(2.0); // Below threshold

        when(itemRepository.findByActive(true)).thenReturn(activeItems);
        doReturn(hotPopularity).when(spyService).getItemPopularity("hot1");
        doReturn(coldPopularity).when(spyService).getItemPopularity("cold1");

        // Act
        List<ItemPopularity> hotItems = spyService.getHotItems();

        // Assert
        assertNotNull(hotItems);
        assertEquals(1, hotItems.size());
        assertEquals("hot1", hotItems.get(0).getItemId());
        assertTrue(hotItems.get(0).isHot());
    }

    @Test
    void getHotItems_WithNoHotItems_ShouldReturnEmptyList() {
        // Arrange - We need to spy the service to mock getItemPopularity
        StatsService spyService = spy(statsService);

        ItemEntity item = new ItemEntity("Cold Item", "Description", 100.0, LocalDateTime.now().plusDays(1), "creator@example.com", Category.BOOKS);
        item.setId("cold1");
        item.setActive(true);

        List<ItemEntity> activeItems = Collections.singletonList(item);

        // Create cold popularity object
        ItemPopularity coldPopularity = new ItemPopularity();
        coldPopularity.setItemId("cold1");
        coldPopularity.setItemName("Cold Item");
        coldPopularity.setHot(false);
        coldPopularity.setBidFrequency(2.0); // Below threshold

        when(itemRepository.findByActive(true)).thenReturn(activeItems);
        doReturn(coldPopularity).when(spyService).getItemPopularity("cold1");

        // Act
        List<ItemPopularity> hotItems = spyService.getHotItems();

        // Assert
        assertNotNull(hotItems);
        assertTrue(hotItems.isEmpty());
    }
}