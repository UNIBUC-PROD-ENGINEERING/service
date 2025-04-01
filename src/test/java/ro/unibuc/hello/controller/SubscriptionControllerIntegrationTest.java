package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.dto.Subscription;

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

import ro.unibuc.hello.service.SubscriptionService;
import ro.unibuc.hello.exception.TierAlreadyExistsException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class SubscriptionControllerIntegrationTest {
    
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
    private SubscriptionService subscriptionService;

    @BeforeEach
    public void cleanUpAndAddTestData() throws TierAlreadyExistsException {
        subscriptionService.deleteAllSubscriptions();

        Subscription subscription1 = new Subscription(1, 90);
        Subscription subscription2 = new Subscription(2, 115);
        Subscription subscription3 = new Subscription(3, 150);

        subscriptionService.saveSubscription(subscription1);
        subscriptionService.saveSubscription(subscription2);
        subscriptionService.saveSubscription(subscription3);
    }

    @Test
    public void testGetAllSubscriptions() throws Exception {
        mockMvc.perform(get("/subscriptions"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(3));
            // .andExpect(jsonPath("$[0].content").value("Hello 1"))
            // .andExpect(jsonPath("$[1].content").value("Hello 2"));
    }

    @Test
    public void testCreateSubscription() throws Exception {
        mockMvc.perform(post("/add-subscription")
                .param("tier", "4")
                .param("price", "200"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(4));
    }

    // @Test
    // public void testDeleteSubscription() throws Exception {

    //     String idToDelete = subscriptionService.getAllSubscriptions().get(0).getId();

    //     mockMvc.perform(delete("/subscriptions/{id}", idToDelete))
    //             .andExpect(status().isOk());

    //     // mockMvc.perform(post("/delete-subscription")
    //     //         .param("id", idToDelete))
    //     //         .andExpect(status().isOk());

    //     mockMvc.perform(get("/subscriptions"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.length()").value(2));
    // }
}
