package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.PlayerService;
import ro.unibuc.hello.service.GameService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
public class GameControllerTest {
    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_CreateGame() throws Exception {
        // Create a GameEntity object
        GameEntity game = new GameEntity("100", "2024-04-01", 2, 3, "90-70", 10);

        // Mock the behavior of GameService.createGame()
        when(gameService.create(any(GameEntity.class))).thenReturn(game);

        // Perform the POST request and expect OK status
        MvcResult result = mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"id\":\"1\",\"date\":\"2024-04-01\",\"team1_id\":2,\"team2_id\":3,\"score\":\"90-70\",\"spectators\":17000}"))
                .andExpect(status().isOk())
                .andReturn();

        // Convert the JSON response content back to a GameEntity object
        ObjectMapper objectMapper = new ObjectMapper();
        GameEntity responseGame = objectMapper.readValue(result.getResponse().getContentAsString(),
                GameEntity.class);
        // Compare the responseGame object with the expected game object
        Assertions.assertEquals(game, responseGame);
    }

    @Test
    void testUpdateGame() throws Exception {
        // Create an updated game object
        GameEntity updatedGame = new GameEntity();
        updatedGame.setDate("2024-03-27");
        updatedGame.setTeam1_id(2);
        updatedGame.setTeam2_id(3);
        updatedGame.setScore("3-2");
        updatedGame.setSpectators(50000);

        when(gameService.updateGame("1", updatedGame)).thenReturn(updatedGame);
        String updatedGameJson = objectMapper.writeValueAsString(updatedGame);

        mockMvc.perform(MockMvcRequestBuilders.put("/game/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedGameJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(updatedGameJson));
    }

    @Test
    void test_DeleteGameById() throws Exception {
        when(gameService.deleteById(anyString())).thenReturn("Game deleted succesfully");

        MvcResult result = mockMvc.perform(delete("/game")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(gameService.deleteById(anyString()), result.getResponse().getContentAsString());
    }

    @Test
    void test_GetGame() throws Exception {
        GameEntity gameEntity = new GameEntity("1", "2024-04-01", 2, 3, "90-70", 17000);

        when(gameService.getGame("1")).thenReturn(gameEntity.toString());
        MvcResult result = mockMvc.perform(get("/game?id=1")
                .content(objectMapper.writeValueAsString(gameEntity.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(gameEntity.toString(), result.getResponse().getContentAsString());
    }

    @Test
    void test_DeleteNonExistingGameByName() throws Exception {
        when(gameService.deleteById(anyString())).thenReturn("Game not found");

        AssertionError exception = assertThrows(AssertionError.class, () -> {
            mockMvc.perform(get("/game?id=NonExistentGame")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        });
        assertFalse(exception.getMessage().contains("Game not found"));
    }

    @Test
    void test_EntityNotFoundException() throws Exception {
        // Mock the behavior of playerService.getPlayer()
        when(gameService.getGame(anyString())).thenThrow(new EntityNotFoundException("Game not found"));

        // Perform the GET request and expect the EntityNotFoundException
        Exception exception = assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/game?id=NonExistentGame")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        });

        assertTrue(exception.getMessage().contains("Game not found"));
    }
}
