package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.dto.Game;

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

import ro.unibuc.hello.service.GamesService;

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
public class GamesControllerIntegrationTest {

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
    private GamesService gamesService;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        gamesService.deleteAllGames();
        
        Game game1 = new Game("Balatro", 1);
        Game game2 = new Game("Half-Life", 1);
        Game game3 = new Game("Half-Life 2", 2);
        Game game4 = new Game("Cyberpunk 2077", 3);

        gamesService.saveGame(game1);
        gamesService.saveGame(game2);
        gamesService.saveGame(game3);
        gamesService.saveGame(game4);
    }

    @Test
    public void testGetAllGames() throws Exception {
        mockMvc.perform(get("/games"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(4))
            .andExpect(jsonPath("$[0].title").value("Balatro"))
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[1].title").value("Half-Life"))
            .andExpect(jsonPath("$[1].tier").value(1))
            .andExpect(jsonPath("$[2].title").value("Half-Life 2"))
            .andExpect(jsonPath("$[2].tier").value(2))
            .andExpect(jsonPath("$[3].title").value("Cyberpunk 2077"))
            .andExpect(jsonPath("$[3].tier").value(3));
    }
    
    @Test
    public void testFindGamesByTier() throws Exception {
        mockMvc.perform(get("/games?tier=2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].title").value("Balatro"))
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[1].title").value("Half-Life"))
            .andExpect(jsonPath("$[1].tier").value(1))
            .andExpect(jsonPath("$[2].title").value("Half-Life 2"))
            .andExpect(jsonPath("$[2].tier").value(2));
    }

    @Test
    public void testSearchGameByTitle() throws Exception {
        mockMvc.perform(get("/games?title=Half-Life"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("Half-Life"))
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[1].title").value("Half-Life 2"))
            .andExpect(jsonPath("$[1].tier").value(2));
    }

    @Test
    public void testSearchGameByTitleAndTier() throws Exception {
        mockMvc.perform(get("/games?title=Half-Life&tier=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].title").value("Half-Life"))
            .andExpect(jsonPath("$[0].tier").value(1));
    }

    @Test
    public void testAddGameAndSearch() throws Exception {
        mockMvc.perform(post("/add-game?title=Counter-Strike&tier=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("Counter-Strike"))
            .andExpect(jsonPath("$.tier").value(1));

        mockMvc.perform(get("/games?tier=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].title").value("Balatro"))
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[1].title").value("Half-Life"))
            .andExpect(jsonPath("$[1].tier").value(1))
            .andExpect(jsonPath("$[2].title").value("Counter-Strike"))
            .andExpect(jsonPath("$[2].tier").value(1));
    }
}
