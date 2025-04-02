package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import ro.unibuc.hello.service.GameService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
//@Tag("IntegrationTest")
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

    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        objectMapper = new ObjectMapper();

        // Clean existing games
        try {
            List<Game> games = gameService.getAllGames();
            for (Game game : games) {
                try {
                    gameService.deleteGame(game.getId());
                } catch (Exception e) {
                    // Ignore if already deleted
                }
            }
        } catch (Exception e) {
            // Ignore any errors during cleanup
        }

        // Add test games
        Game game1 = new Game("Game 1", "PC", "Action", 2023);
        Game game2 = new Game("Game 2", "PlayStation", "RPG", 2022);

        gameService.createGame(game1);
        gameService.createGame(game2);
    }

    @Test
    public void testGetAllGames() throws Exception {
        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Game 1"))
                .andExpect(jsonPath("$[1].name").value("Game 2"));
    }

    @Test
    public void testGetGameById() throws Exception {
        // Get all games to find one by name
        List<Game> games = gameService.getAllGames();
        Game game = games.stream()
                .filter(g -> g.getName().equals("Game 1"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test game not found"));

        mockMvc.perform(get("/games/{id}", game.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(game.getId()))
                .andExpect(jsonPath("$.name").value("Game 1"))
                .andExpect(jsonPath("$.platform").value("PC"))
                .andExpect(jsonPath("$.genre").value("Action"))
                .andExpect(jsonPath("$.releasedYear").value(2023));
    }

    @Test
    public void testGetGameById_NotFound() throws Exception {
        String nonExistingId = "nonExistingId";

        mockMvc.perform(get("/games/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddGame() throws Exception {
        Game newGame = new Game("New Test Game", "Xbox", "Strategy", 2024);

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGame)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("New Test Game"))
                .andExpect(jsonPath("$.platform").value("Xbox"))
                .andExpect(jsonPath("$.genre").value("Strategy"))
                .andExpect(jsonPath("$.releasedYear").value(2024));

        // Verify it was added to the database
        List<Game> games = gameService.getAllGames();
        assertEquals(3, games.size());

        boolean found = games.stream()
                .anyMatch(g -> g.getName().equals("New Test Game") &&
                        g.getPlatform().equals("Xbox") &&
                        g.getGenre().equals("Strategy") &&
                        g.getReleasedYear().equals(2024));

        assertEquals(true, found, "New game should be in the database");
    }

    @Test
    public void testUpdateGame() throws Exception {
        // Get a game to update
        List<Game> games = gameService.getAllGames();
        Game game = games.stream()
                .filter(g -> g.getName().equals("Game 1"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test game not found"));

        // Create updated version
        Game updatedGame = new Game("Updated Game", "Switch", "Adventure", 2021);

        mockMvc.perform(put("/games/{id}", game.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGame)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(game.getId()))
                .andExpect(jsonPath("$.name").value("Updated Game"))
                .andExpect(jsonPath("$.platform").value("Switch"))
                .andExpect(jsonPath("$.genre").value("Adventure"))
                .andExpect(jsonPath("$.releasedYear").value(2021));

        // Verify it was updated in the database
        Game retrievedGame = gameService.getGameById(game.getId());
        assertEquals("Updated Game", retrievedGame.getName());
        assertEquals("Switch", retrievedGame.getPlatform());
        assertEquals("Adventure", retrievedGame.getGenre());
        assertEquals(2021, retrievedGame.getReleasedYear());
    }

    @Test
    public void testDeleteGame() throws Exception {
        // Get a game to delete
        List<Game> games = gameService.getAllGames();
        Game game = games.stream()
                .filter(g -> g.getName().equals("Game 2"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test game not found"));

        // Delete the game
        mockMvc.perform(delete("/games/{id}", game.getId()))
                .andExpect(status().isNoContent());

        // Verify it was deleted
        mockMvc.perform(get("/games/{id}", game.getId()))
                .andExpect(status().isNotFound());

        // Verify count of games is reduced
        List<Game> remainingGames = gameService.getAllGames();
        assertEquals(games.size() - 1, remainingGames.size(), "One game should be deleted");
    }

    @Test
    public void testAddGame_ValidationError() throws Exception {
        // Create an invalid game with missing required fields
        Game invalidGame = new Game("", "", "", null);

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGame)))
                .andExpect(status().isBadRequest());
    }
}