package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.data.Game;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.LoginResponse;
import ro.unibuc.hello.dto.RegisterRequest;
import ro.unibuc.hello.service.AuthenticationService;
import ro.unibuc.hello.service.GameService;

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
public class GameControllerIntegrationTest {

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
    private GameService gameService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    private String token;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        // Clear existing games
        gameService.deleteAllGames();

        // Add test games
        Game game1 = new Game("The Witcher 3", "PC", "RPG", 2015);
        game1.setId("1");
        Game game2 = new Game("Red Dead Redemption 2", "PlayStation", "Action-Adventure", 2018);
        game2.setId("2");

        gameService.createGame(game1);
        gameService.createGame(game2);

        // Create test user and login to get token for authentication
        userRepository.deleteAll();
        authenticationService.register(new RegisterRequest("testUser", "password123"));
        LoginResponse response = authenticationService.login(new LoginRequest("testUser", "password123"));

        token = response.getToken();
    }

    @Test
    public void testGetAllGames() throws Exception {
        mockMvc.perform(get("/games")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("The Witcher 3"))
                .andExpect(jsonPath("$[1].name").value("Red Dead Redemption 2"));
    }

    @Test
    public void testGetGameById() throws Exception {
        mockMvc.perform(get("/games/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("The Witcher 3"))
                .andExpect(jsonPath("$.platform").value("PC"))
                .andExpect(jsonPath("$.genre").value("RPG"))
                .andExpect(jsonPath("$.releasedYear").value(2015));
    }

    @Test
    public void testCreateGame() throws Exception {
        Game newGame = new Game("Elden Ring", "PlayStation", "Action RPG", 2022);
        newGame.setId("3");

        mockMvc.perform(post("/games")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newGame)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.name").value("Elden Ring"))
                .andExpect(jsonPath("$.platform").value("PlayStation"))
                .andExpect(jsonPath("$.genre").value("Action RPG"))
                .andExpect(jsonPath("$.releasedYear").value(2022));

        // Verify the new game was added to the list
        mockMvc.perform(get("/games")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void testUpdateGame() throws Exception {
        Game updatedGame = new Game("The Witcher 3: Wild Hunt", "PC", "Open World RPG", 2015);
        updatedGame.setId("1");

        mockMvc.perform(put("/games/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedGame)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("The Witcher 3: Wild Hunt"))
                .andExpect(jsonPath("$.genre").value("Open World RPG"));

        // Verify the game was updated
        mockMvc.perform(get("/games/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("The Witcher 3: Wild Hunt"))
                .andExpect(jsonPath("$.genre").value("Open World RPG"));
    }

    @Test
    public void testDeleteGame() throws Exception {
        mockMvc.perform(delete("/games/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        // Verify the game was deleted
        mockMvc.perform(get("/games")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Red Dead Redemption 2"));
    }

    @Test
    public void testGetNonExistentGame() throws Exception {
        mockMvc.perform(get("/games/999")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateNonExistentGame() throws Exception {
        Game nonExistentGame = new Game("Fake Game", "PC", "Fake Genre", 2023);
        nonExistentGame.setId("999");

        mockMvc.perform(put("/games/999")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(nonExistentGame)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteNonExistentGame() throws Exception {
        mockMvc.perform(delete("/games/999")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/games"))
                .andExpect(status().isForbidden());
    }
}