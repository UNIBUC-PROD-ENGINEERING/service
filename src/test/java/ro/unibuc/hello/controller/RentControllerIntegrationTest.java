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

import ro.unibuc.hello.data.Game;
import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.dto.RentRequest;
import ro.unibuc.hello.service.GameService;
import ro.unibuc.hello.service.RentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class RentControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withEnv("MONGO_INITDB_ROOT_USERNAME", "root")
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "example")
            .withEnv("MONGO_INITDB_DATABASE", "testdb")
            .withCommand("--auth");

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
        final String MONGO_URL = "mongodb://root:example@localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RentService rentService;

    @Autowired
    private GameService gameService;

    private ObjectMapper objectMapper;

    private Game game1;
    private Game game2;
    private static final String USER_ID_1 = "user123";
    private static final String USER_ID_2 = "user456";

    @BeforeEach
    public void cleanUpAndAddTestData() {
        // Initialize ObjectMapper with JavaTimeModule for LocalDateTime serialization
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Clean existing data
        try {
            rentService.getAllRents().forEach(rent -> {
                try {
                    rentService.returnGame(rent.getUserId(), rent.getGameId());
                } catch (Exception e) {
                    // Ignore if already returned
                }
            });
        } catch (Exception e) {
            // Ignore any errors during cleanup
        }

        // Create test games
        game1 = new Game("Game 1", "PC", "Action", 2023);
        game2 = new Game("Game 2", "PlayStation", "RPG", 2022);

        game1 = gameService.createGame(game1);
        game2 = gameService.createGame(game2);
    }

    @Test
    public void testRentAndReturnGame() throws Exception {
        // Prepare rent request
        RentRequest rentRequest = new RentRequest(USER_ID_1, game1.getId());

        // Test renting a game
        mockMvc.perform(post("/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(USER_ID_1))
                .andExpect(jsonPath("$.gameId").value(game1.getId()))
                .andExpect(jsonPath("$.returned").value(false));

        // Test that the rent appears in user's rentals
        mockMvc.perform(get("/rent/user/{userId}", USER_ID_1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(USER_ID_1))
                .andExpect(jsonPath("$[0].gameId").value(game1.getId()))
                .andExpect(jsonPath("$[0].returned").value(false));

        // Test that the same user can't rent the same game twice
        mockMvc.perform(post("/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andExpect(status().isBadRequest());

        // Test returning the game
        mockMvc.perform(post("/rent/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(USER_ID_1))
                .andExpect(jsonPath("$.gameId").value(game1.getId()))
                .andExpect(jsonPath("$.returned").value(true));

        // Test that the same user can rent the same game again after returning it
        mockMvc.perform(post("/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testMultipleUsersRentingDifferentGames() throws Exception {
        // User 1 rents Game 1
        RentRequest rentRequest1 = new RentRequest(USER_ID_1, game1.getId());
        mockMvc.perform(post("/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest1)))
                .andExpect(status().isCreated());

        // User 2 rents Game 2
        RentRequest rentRequest2 = new RentRequest(USER_ID_2, game2.getId());
        mockMvc.perform(post("/rent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest2)))
                .andExpect(status().isCreated());

        // Check that User 1 has rented Game 1
        mockMvc.perform(get("/rent/user/{userId}", USER_ID_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(USER_ID_1))
                .andExpect(jsonPath("$[0].gameId").value(game1.getId()));

        // Check that User 2 has rented Game 2
        mockMvc.perform(get("/rent/user/{userId}", USER_ID_2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(USER_ID_2))
                .andExpect(jsonPath("$[0].gameId").value(game2.getId()));

        // Check that Game 1 is rented by User 1
        mockMvc.perform(get("/rent/game/{gameId}", game1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(USER_ID_1))
                .andExpect(jsonPath("$[0].gameId").value(game1.getId()));
    }

    @Test
    public void testGetAllRents() throws Exception {
        // Rent a couple of games
        rentService.rentGame(USER_ID_1, game1.getId());
        rentService.rentGame(USER_ID_2, game2.getId());

        // Test getting all rents
        mockMvc.perform(get("/rent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetRentById() throws Exception {
        // Rent a game
        Rent rent = rentService.rentGame(USER_ID_1, game1.getId());

        // Test getting the rent by ID
        mockMvc.perform(get("/rent/{id}", rent.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(rent.getId()))
                .andExpect(jsonPath("$.userId").value(USER_ID_1))
                .andExpect(jsonPath("$.gameId").value(game1.getId()));
    }

    @Test
    public void testReturnNonExistentRent() throws Exception {
        // Try to return a game that hasn't been rented
        RentRequest rentRequest = new RentRequest("nonexistentuser", "nonexistentgame");

        mockMvc.perform(post("/rent/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andExpect(status().isNotFound());
    }
}