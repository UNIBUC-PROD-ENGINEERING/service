package ro.unibuc.hello.controller;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ro.unibuc.hello.controller.GamesController;
import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GamesService;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


class GamesControllerTest {

    @Mock
    private GamesService gamesService;

    @InjectMocks
    private GamesController gameController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    void testGetAllGames() throws Exception {
        Game game1 = new Game("1", "Balatro", 1);
        Game game2 = new Game("2", "Half-Life", 1);
        Game game3 = new Game("3", "Half-Life 2", 2);
        Game game4 = new Game("4", "Cyberpunk 2077", 3);

        when(gamesService.getAllGames()).thenReturn(new ArrayList<>(Arrays.asList(game1, game2, game3, game4)));
    
        mockMvc
            .perform(get("/games"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].title").value("Balatro"))
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[1].title").value("Half-Life"))
            .andExpect(jsonPath("$[1].tier").value(1))
            .andExpect(jsonPath("$[2].id").value("3"))
            .andExpect(jsonPath("$[2].title").value("Half-Life 2"))
            .andExpect(jsonPath("$[2].tier").value(2))
            .andExpect(jsonPath("$[3].id").value("4"))
            .andExpect(jsonPath("$[3].title").value("Cyberpunk 2077"))
            .andExpect(jsonPath("$[3].tier").value(3));
    }
    
    @Test
    void testGetAvailableGames() throws Exception{
        Game game1 = new Game("1", "Balatro", 1);
        Game game2 = new Game("2", "Half-Life", 1);
        Game game3 = new Game("3", "Half-Life 2", 2);
        
        when(gamesService.getGamesAvailable(2)).thenReturn(new ArrayList<>(Arrays.asList(game1, game2, game3)));

        mockMvc
            .perform(get("/games?tier=2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].title").value("Balatro"))
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[1].title").value("Half-Life"))
            .andExpect(jsonPath("$[1].tier").value(1))
            .andExpect(jsonPath("$[2].id").value("3"))
            .andExpect(jsonPath("$[2].title").value("Half-Life 2"))
            .andExpect(jsonPath("$[2].tier").value(2));
    }

    @Test
    void testGetGamebyId() throws Exception{
        Game game1 = new Game("1", "Balatro", 1);

        when(gamesService.getGamebyId("1")).thenReturn(new ArrayList<>(Arrays.asList(game1)));

        mockMvc
            .perform(get("/games?id=1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].title").value("Balatro"))
            .andExpect(jsonPath("$[0].tier").value(1));
    }

    @Test
    void testGetGamesbyTitle() throws Exception{
        Game game2 = new Game("2", "Half-Life", 1);
        Game game3 = new Game("3", "Half-Life 2", 2);
        
        when(gamesService.getGamesByTitle("Half-Life")).thenReturn(new ArrayList<>(Arrays.asList(game2, game3)));

        mockMvc
            .perform(get("/games?title=Half-Life"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("2"))
            .andExpect(jsonPath("$[0].title").value("Half-Life"))
            .andExpect(jsonPath("$[0].tier").value(1))
            .andExpect(jsonPath("$[1].id").value("3"))
            .andExpect(jsonPath("$[1].title").value("Half-Life 2"))
            .andExpect(jsonPath("$[1].tier").value(2));
    }
    
    @Test
    void testGetsByTitleAndTier() throws Exception{
        Game game2 = new Game("2", "Half-Life", 1);

        when(gamesService.getGamesByTitleAndTier("Half-Life", 1)).thenReturn(new ArrayList<>(Arrays.asList(game2)));

        mockMvc
            .perform(get("/games?title=Half-Life&tier=1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("2"))
            .andExpect(jsonPath("$[0].title").value("Half-Life"))
            .andExpect(jsonPath("$[0].tier").value(1));
    }

    @Test
    void testSaveGame() throws Exception{
        Game game4 = new Game("4", "Cyberpunk 2077", 3);

        when(gamesService.saveGame(game4)).thenReturn(game4);

        mockMvc
            .perform(post("/add-game?title=Cyberpunk 2077&tier=3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("4"))
            .andExpect(jsonPath("$.title").value("Cyberpunk 2077"))
            .andExpect(jsonPath("$.tier").value(3));
    
    }

    @Test
    void testDeleteGameExisting() throws Exception{
        when(gamesService.deleteGame("1")).thenReturn(true);

        mockMvc
            .perform(post("/delete-game?id=1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Success."));
    }

    @Test
    void testDeleteGameNotExisting() throws Exception{
        when(gamesService.deleteGame("0")).thenReturn(false);

        mockMvc
            .perform(post("/delete-game?id=0"))
            .andExpect(status().isOk())
            .andExpect(content().string("No game with this id."));
    }

}
