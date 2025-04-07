package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.data.Category;

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

import ro.unibuc.hello.service.ItemService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class ItemControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withSharding();

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    private ObjectMapper objectMapper;
    private Item testItem1;
    private Item testItem2;
    private Item inactiveItem;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        // Configure ObjectMapper for LocalDateTime serialization
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Clear existing data
        List<Item> allItems = itemService.getAllItems();
        for (Item item : allItems) {
            itemService.deleteItem(item.getId());
        }

        // Create test items
        Item item1 = new Item();
        item1.setName("Test Electronics Item");
        item1.setDescription("This is a test electronics item");
        item1.setInitialPrice(100.0);
        item1.setEndTime(LocalDateTime.now().plusDays(7)); // Active for 7 days
        item1.setActive(true);
        item1.setCreator("seller1@example.com");
        item1.setCategory(Category.ELECTRONICS);

        Item item2 = new Item();
        item2.setName("Test Book Item");
        item2.setDescription("This is a test book item");
        item2.setInitialPrice(50.0);
        item2.setEndTime(LocalDateTime.now().plusDays(5)); // Active for 5 days
        item2.setActive(true);
        item2.setCreator("seller2@example.com");
        item2.setCategory(Category.BOOKS);

        Item item3 = new Item();
        item3.setName("Inactive Item");
        item3.setDescription("This is an inactive item");
        item3.setInitialPrice(75.0);
        item3.setEndTime(LocalDateTime.now().plusDays(10));
        item3.setActive(false); // Explicitly inactive
        item3.setCreator("seller3@example.com");
        item3.setCategory(Category.OTHER);

        // Save test items
        testItem1 = itemService.createItem(item1);
        testItem2 = itemService.createItem(item2);
        inactiveItem = itemService.createItem(item3);
    }

    @Test
    public void testGetAllItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists())
                .andExpect(jsonPath("$[2].name").exists());
    }


    @Test
    public void testGetItemById() throws Exception {
        mockMvc.perform(get("/items/" + testItem1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testItem1.getId()))
                .andExpect(jsonPath("$.name").value("Test Electronics Item"))
                .andExpect(jsonPath("$.description").value("This is a test electronics item"))
                .andExpect(jsonPath("$.initialPrice").value(100.0))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.creator").value("seller1@example.com"))
                .andExpect(jsonPath("$.category").value("ELECTRONICS"));
    }

    @Test
    public void testGetItemByIdNotFound() throws Exception {
        mockMvc.perform(get("/items/nonexistent-item-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchItemByName() throws Exception {
        mockMvc.perform(get("/items/search?name=book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Book Item"))
                .andExpect(jsonPath("$.category").value("BOOKS"));
    }

    @Test
    public void testSearchItemByNameNotFound() throws Exception {
        mockMvc.perform(get("/items/search?name=nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateItem() throws Exception {
        Item newItem = new Item();
        newItem.setName("New Test Item");
        newItem.setDescription("This is a newly created test item");
        newItem.setInitialPrice(200.0);
        newItem.setEndTime(LocalDateTime.now().plusDays(14));
        newItem.setActive(true);
        newItem.setCreator("newcreator@example.com");
        newItem.setCategory(Category.FASHION);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("New Test Item"))
                .andExpect(jsonPath("$.description").value("This is a newly created test item"))
                .andExpect(jsonPath("$.initialPrice").value(200.0))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.category").value("FASHION"));

        // Verify item was added
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    public void testCreateItemWithInvalidEmail() throws Exception {
        Item invalidItem = new Item();
        invalidItem.setName("Invalid Email Item");
        invalidItem.setDescription("This item has an invalid email");
        invalidItem.setInitialPrice(150.0);
        invalidItem.setEndTime(LocalDateTime.now().plusDays(10));
        invalidItem.setActive(true);
        invalidItem.setCreator("invalid-email"); // Invalid email format
        invalidItem.setCategory(Category.HOME);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateItemWithPastEndTime() throws Exception {
        Item pastEndTimeItem = new Item();
        pastEndTimeItem.setName("Past End Time Item");
        pastEndTimeItem.setDescription("This item has an end time in the past");
        pastEndTimeItem.setInitialPrice(125.0);
        pastEndTimeItem.setEndTime(LocalDateTime.now().minusDays(1)); // End time in the past
        pastEndTimeItem.setActive(true);
        pastEndTimeItem.setCreator("valid@example.com");
        pastEndTimeItem.setCategory(Category.TOYS);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pastEndTimeItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateItem() throws Exception {
        Item updatedItem = new Item();
        updatedItem.setId(testItem1.getId());
        updatedItem.setName("Updated Electronics Item");
        updatedItem.setDescription("This item has been updated");
        updatedItem.setInitialPrice(120.0);
        updatedItem.setEndTime(LocalDateTime.now().plusDays(10));
        updatedItem.setActive(true);
        updatedItem.setCreator("seller1@example.com");
        updatedItem.setCategory(Category.ELECTRONICS);

        mockMvc.perform(put("/items/" + testItem1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testItem1.getId()))
                .andExpect(jsonPath("$.name").value("Updated Electronics Item"))
                .andExpect(jsonPath("$.description").value("This item has been updated"))
                .andExpect(jsonPath("$.initialPrice").value(120.0));

        // Verify item was updated
        mockMvc.perform(get("/items/" + testItem1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Electronics Item"));
    }

    @Test
    public void testUpdateItemNotFound() throws Exception {
        Item nonexistentItem = new Item();
        nonexistentItem.setId("nonexistent-id");
        nonexistentItem.setName("Nonexistent Item");
        nonexistentItem.setDescription("This item doesn't exist");
        nonexistentItem.setInitialPrice(100.0);
        nonexistentItem.setEndTime(LocalDateTime.now().plusDays(7));
        nonexistentItem.setActive(true);
        nonexistentItem.setCreator("valid@example.com");
        nonexistentItem.setCategory(Category.OTHER);

        mockMvc.perform(put("/items/nonexistent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonexistentItem)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateItemWithInvalidEmail() throws Exception {
        Item invalidEmailItem = new Item();
        invalidEmailItem.setId(testItem1.getId());
        invalidEmailItem.setName(testItem1.getName());
        invalidEmailItem.setDescription(testItem1.getDescription());
        invalidEmailItem.setInitialPrice(testItem1.getInitialPrice());
        invalidEmailItem.setEndTime(testItem1.getEndTime());
        invalidEmailItem.setActive(testItem1.isActive());
        invalidEmailItem.setCreator("invalid-email"); // Invalid email format
        invalidEmailItem.setCategory(testItem1.getCategory());

        mockMvc.perform(put("/items/" + testItem1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateActiveItemWithPastEndTime() throws Exception {
        Item pastEndTimeItem = new Item();
        pastEndTimeItem.setId(testItem1.getId());
        pastEndTimeItem.setName(testItem1.getName());
        pastEndTimeItem.setDescription(testItem1.getDescription());
        pastEndTimeItem.setInitialPrice(testItem1.getInitialPrice());
        pastEndTimeItem.setEndTime(LocalDateTime.now().minusDays(1)); // End time in the past
        pastEndTimeItem.setActive(true); // Active with past end time should fail
        pastEndTimeItem.setCreator(testItem1.getCreator());
        pastEndTimeItem.setCategory(testItem1.getCategory());

        mockMvc.perform(put("/items/" + testItem1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pastEndTimeItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteItem() throws Exception {
        mockMvc.perform(delete("/items/" + testItem2.getId()))
                .andExpect(status().isNoContent());

        // Verify item was deleted
        mockMvc.perform(get("/items/" + testItem2.getId()))
                .andExpect(status().isNotFound());

        // Verify total count decreased
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testDeleteItemNotFound() throws Exception {
        mockMvc.perform(delete("/items/nonexistent-item-id"))
                .andExpect(status().isNotFound());
    }
}