package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.Category;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.dto.Bid;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.service.BidService;
import ro.unibuc.hello.service.ItemService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class StatsControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withSharding();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    @Autowired
    private BidService bidService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BidRepository bidRepository;

    private ObjectMapper objectMapper;
    private Item electronicsItem;
    private Item fashionItem;
    private Item booksItem;
    private String user1Email = "user1@example.com";
    private String user2Email = "user2@example.com";
    private String sellerEmail = "seller@example.com";

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        final String MONGO_URL = "mongodb://localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @BeforeEach
    public void cleanUpAndSetupTestData() {
        // Configure ObjectMapper for LocalDateTime serialization
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Clear existing data
        bidRepository.deleteAll();
        itemRepository.deleteAll();

        // Create test items
        createTestItems();

        // Create test bids
        createTestBids();
    }

    private void createTestItems() {
        // Electronics item
        Item item1 = new Item();
        item1.setName("Smartphone");
        item1.setDescription("Latest smartphone model");
        item1.setInitialPrice(500.0);
        item1.setEndTime(LocalDateTime.now().plusDays(7));
        item1.setActive(true);
        item1.setCreator(sellerEmail);
        item1.setCategory(Category.ELECTRONICS);
        electronicsItem = itemService.createItem(item1);

        // Fashion item
        Item item2 = new Item();
        item2.setName("Designer Jacket");
        item2.setDescription("Stylish jacket");
        item2.setInitialPrice(200.0);
        item2.setEndTime(LocalDateTime.now().plusDays(5));
        item2.setActive(true);
        item2.setCreator(sellerEmail);
        item2.setCategory(Category.FASHION);
        fashionItem = itemService.createItem(item2);

        // Books item
        Item item3 = new Item();
        item3.setName("Programming Book");
        item3.setDescription("Learn to code");
        item3.setInitialPrice(50.0);
        item3.setEndTime(LocalDateTime.now().plusDays(10));
        item3.setActive(true);
        item3.setCreator(sellerEmail);
        item3.setCategory(Category.BOOKS);
        booksItem = itemService.createItem(item3);
    }

    private void createTestBids() {
        // Bids for electronics item
        Bid bid1 = new Bid();
        bid1.setItemId(electronicsItem.getId());
        bid1.setBidderName("User 1");
        bid1.setEmail(user1Email);
        bid1.setAmount(550.0);
        bidService.placeBid(bid1);

        Bid bid2 = new Bid();
        bid2.setItemId(electronicsItem.getId());
        bid2.setBidderName("User 2");
        bid2.setEmail(user2Email);
        bid2.setAmount(600.0);
        bidService.placeBid(bid2);

        Bid bid3 = new Bid();
        bid3.setItemId(electronicsItem.getId());
        bid3.setBidderName("User 1");
        bid3.setEmail(user1Email);
        bid3.setAmount(650.0);
        bidService.placeBid(bid3);

        // Bids for fashion item
        Bid bid4 = new Bid();
        bid4.setItemId(fashionItem.getId());
        bid4.setBidderName("User 1");
        bid4.setEmail(user1Email);
        bid4.setAmount(220.0);
        bidService.placeBid(bid4);

        Bid bid5 = new Bid();
        bid5.setItemId(fashionItem.getId());
        bid5.setBidderName("User 2");
        bid5.setEmail(user2Email);
        bid5.setAmount(240.0);
        bidService.placeBid(bid5);

        // Bids for books item
        Bid bid6 = new Bid();
        bid6.setItemId(booksItem.getId());
        bid6.setBidderName("User 2");
        bid6.setEmail(user2Email);
        bid6.setAmount(60.0);
        bidService.placeBid(bid6);
    }

    @Test
    public void testGetOverallStats() throws Exception {
        mockMvc.perform(get("/stats/overview"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalItems").value(3))
                .andExpect(jsonPath("$.activeItems").value(3))
                .andExpect(jsonPath("$.totalBids").value(6))
                .andExpect(jsonPath("$.averageBids").value(2.0))
                .andExpect(jsonPath("$.uniqueBidders").value(2));
    }

    @Test
    public void testGetUserStats() throws Exception {
        mockMvc.perform(get("/stats/user/" + user1Email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(user1Email))
                .andExpect(jsonPath("$.totalBidsMade").value(3))
                .andExpect(jsonPath("$.uniqueItemsBidOn").value(2));
    }

    @Test
    public void testGetUserStatsWithInvalidEmail() throws Exception {
        mockMvc.perform(get("/stats/user/invalid-email"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetItemPopularity() throws Exception {
        mockMvc.perform(get("/stats/item/" + electronicsItem.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.itemId").value(electronicsItem.getId()))
                .andExpect(jsonPath("$.itemName").value("Smartphone"))
                .andExpect(jsonPath("$.totalBids").value(3))
                .andExpect(jsonPath("$.uniqueBidders").value(2));
    }

    @Test
    public void testGetItemPopularityForNonExistentItem() throws Exception {
        mockMvc.perform(get("/stats/item/nonexistent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCategoryStats() throws Exception {
        mockMvc.perform(get("/stats/category/ELECTRONICS"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.totalBids").value(3));
    }

    @Test
    public void testGetCategoryStatsForInvalidCategory() throws Exception {
        mockMvc.perform(get("/stats/category/INVALID_CATEGORY"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPopularItems() throws Exception {
        mockMvc.perform(get("/stats/popular-items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].itemName").exists())
                .andExpect(jsonPath("$[0].totalBids").exists())
                .andExpect(jsonPath("$[0].uniqueBidders").exists())
                .andExpect(jsonPath("$[0].popularityScore").exists());
    }

    @Test
    public void testGetPopularItemsWithLimit() throws Exception {
        mockMvc.perform(get("/stats/popular-items?limit=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetPopularCategories() throws Exception {
        mockMvc.perform(get("/stats/popular-categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ELECTRONICS").exists());
    }

    @Test
    public void testGetMostActiveUsers() throws Exception {
        mockMvc.perform(get("/stats/active-users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").exists())
                .andExpect(jsonPath("$[0].totalBidsMade").exists())
                .andExpect(jsonPath("$[0].userActivityScore").exists());
    }

    @Test
    public void testGetMostActiveUsersWithLimit() throws Exception {
        mockMvc.perform(get("/stats/active-users?limit=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetBiddingHourDistribution() throws Exception {
        mockMvc.perform(get("/stats/bidding-hours"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetHotItems() throws Exception {
        mockMvc.perform(get("/stats/hot-items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}