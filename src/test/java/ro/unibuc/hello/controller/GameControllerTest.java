package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.data.Game;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.RestExceptionHandler;
import ro.unibuc.hello.service.GameService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Game testGame;
    private static final String GAME_ID = "game123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mockMvc with exception handler
        mockMvc = MockMvcBuilders
                .standaloneSetup(gameController)
                .setControllerAdvice(new RestExceptionHandler())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();

        objectMapper = new ObjectMapper();

        testGame = new Game("Test Game", "PC", "Action", 2023);
        testGame.setId(GAME_ID);
    }

    @Test
    void testGetAllGames() throws Exception {
        // Arrange
        Game game1 = testGame;
        Game game2 = new Game("Another Game", "PlayStation", "RPG", 2022);
        game2.setId("game456");

        List<Game> games = Arrays.asList(game1, game2);

        when(gameService.getAllGames()).thenReturn(games);

        // Act & Assert
        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(GAME_ID))
                .andExpect(jsonPath("$[0].name").value("Test Game"))
                .andExpect(jsonPath("$[1].id").value("game456"))
                .andExpect(jsonPath("$[1].name").value("Another Game"));

        verify(gameService, times(1)).getAllGames();
    }

    @Test
    void testGetGameById() throws Exception {
        // Arrange
        when(gameService.getGameById(GAME_ID)).thenReturn(testGame);

        // Act & Assert
        mockMvc.perform(get("/games/{id}", GAME_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GAME_ID))
                .andExpect(jsonPath("$.name").value("Test Game"))
                .andExpect(jsonPath("$.platform").value("PC"))
                .andExpect(jsonPath("$.genre").value("Action"))
                .andExpect(jsonPath("$.releasedYear").value(2023));

        verify(gameService, times(1)).getGameById(GAME_ID);
    }

    @Test
    void testGetGameById_NotFound() throws Exception {
        // Arrange
        String nonExistingId = "nonExistingId";
        doThrow(new EntityNotFoundException("Game not found with id: " + nonExistingId))
                .when(gameService).getGameById(nonExistingId);

        // Act & Assert
        mockMvc.perform(get("/games/{id}", nonExistingId))
                .andExpect(status().isNotFound());

        verify(gameService, times(1)).getGameById(nonExistingId);
    }

    @Test
    void testAddGame() throws Exception {
        // Arrange
        Game newGame = new Game("New Game", "Xbox", "Strategy", 2024);
        Game savedGame = new Game("New Game", "Xbox", "Strategy", 2024);
        savedGame.setId("newGameId");

        when(gameService.createGame(any(Game.class))).thenReturn(savedGame);

        // Act & Assert
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGame)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("newGameId"))
                .andExpect(jsonPath("$.name").value("New Game"))
                .andExpect(jsonPath("$.platform").value("Xbox"))
                .andExpect(jsonPath("$.genre").value("Strategy"))
                .andExpect(jsonPath("$.releasedYear").value(2024));

        verify(gameService, times(1)).createGame(any(Game.class));
    }

    @Test
    void testAddGame_ValidationError() throws Exception {
        // Arrange
        Game invalidGame = new Game("", "", "", null); // Invalid game with missing required fields

        // Act & Assert
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGame)))
                .andExpect(status().isBadRequest());

        verify(gameService, never()).createGame(any(Game.class));
    }

    @Test
    void testUpdateGame() throws Exception {
        // Arrange
        Game updatedGame = new Game("Updated Game", "Switch", "Adventure", 2021);
        updatedGame.setId(GAME_ID);

        when(gameService.updateGame(eq(GAME_ID), any(Game.class))).thenReturn(updatedGame);

        // Act & Assert
        mockMvc.perform(put("/games/{id}", GAME_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGame)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GAME_ID))
                .andExpect(jsonPath("$.name").value("Updated Game"))
                .andExpect(jsonPath("$.platform").value("Switch"))
                .andExpect(jsonPath("$.genre").value("Adventure"))
                .andExpect(jsonPath("$.releasedYear").value(2021));

        verify(gameService, times(1)).updateGame(eq(GAME_ID), any(Game.class));
    }

    @Test
    void testUpdateGame_NotFound() throws Exception {
        // Arrange
        String nonExistingId = "nonExistingId";
        Game updatedGame = new Game("Updated Game", "Switch", "Adventure", 2021);

        doThrow(new EntityNotFoundException("Game not found with id: " + nonExistingId))
                .when(gameService).updateGame(eq(nonExistingId), any(Game.class));

        // Act & Assert
        mockMvc.perform(put("/games/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGame)))
                .andExpect(status().isNotFound());

        verify(gameService, times(1)).updateGame(eq(nonExistingId), any(Game.class));
    }

    @Test
    void testDeleteGame() throws Exception {
        // Arrange
        doNothing().when(gameService).deleteGame(GAME_ID);

        // Act & Assert
        mockMvc.perform(delete("/games/{id}", GAME_ID))
                .andExpect(status().isNoContent());

        verify(gameService, times(1)).deleteGame(GAME_ID);
    }

    @Test
    void testDeleteGame_NotFound() throws Exception {
        // Arrange
        String nonExistingId = "nonExistingId";
        doThrow(new EntityNotFoundException("Game not found with id: " + nonExistingId))
                .when(gameService).deleteGame(nonExistingId);

        // Act & Assert
        mockMvc.perform(delete("/games/{id}", nonExistingId))
                .andExpect(status().isNotFound());

        verify(gameService, times(1)).deleteGame(nonExistingId);
    }
}