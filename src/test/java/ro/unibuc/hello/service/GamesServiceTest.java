package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GamesService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class GamesServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GamesService gamesService = new GamesService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }

    @Test
    void testGetAllGames() {
        GameEntity entity1 = new GameEntity("1", "Balatro", 1);
        GameEntity entity2 = new GameEntity("2", "Half-Life", 1);
        GameEntity entity3 = new GameEntity("3", "Half-Life 2", 2);
        GameEntity entity4 = new GameEntity("4", "Cyberpunk 2077", 3);

        when(gameRepository.findAll()).thenReturn(new ArrayList<GameEntity>(Arrays.asList(entity1, entity2, entity3, entity4)));

        List<Game> games = gamesService.getAllGames();

        assertNotNull(games);
        assertEquals(4, games.size());

        assertEquals("1", games.get(0).getId());
        assertEquals("Balatro", games.get(0).getTitle());
        assertEquals(1, games.get(0).getTier());

        assertEquals("2", games.get(1).getId());
        assertEquals("Half-Life", games.get(1).getTitle());
        assertEquals(1, games.get(1).getTier());

        assertEquals("3", games.get(2).getId());
        assertEquals("Half-Life 2", games.get(2).getTitle());
        assertEquals(2, games.get(2).getTier());

        assertEquals("4", games.get(3).getId());
        assertEquals("Cyberpunk 2077", games.get(3).getTitle());
        assertEquals(3, games.get(3).getTier());
    }

    @Test
    void testGetGameById_existing() {
        GameEntity entity1 = new GameEntity("1", "Balatro", 1);

        when(gameRepository.findById("1")).thenReturn(Optional.of(entity1));

        List<Game> games = gamesService.getGamebyId("1");

        assertNotNull(games);
        assertEquals(1, games.size());

        assertEquals("1", games.get(0).getId());
        assertEquals("Balatro", games.get(0).getTitle());
        assertEquals(1, games.get(0).getTier());
    }

    @Test
    void testGetGameById_notExisting() {
        GameEntity entity1 = new GameEntity("1", "Balatro", 1);

        when(gameRepository.findById("0")).thenReturn(Optional.empty());

        List<Game> games = gamesService.getGamebyId("0");

        assertNotNull(games);
        assertEquals(1, games.size());
        assertEquals(null, games.get(0).getId());
        assertEquals(null, games.get(0).getTitle());
        assertEquals(0, games.get(0).getTier());
    }

}
