package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.Game;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private Game testGame;
    private static final String GAME_ID = "game123";

    @BeforeEach
    void setUp() {
        // Create test game
        testGame = new Game("Test Game", "PC", "Action", 2023);
        testGame.setId(GAME_ID);
    }

    @Test
    void testGetAllGames() {
        // Arrange
        List<Game> games = Arrays.asList(
                testGame,
                new Game("Another Game", "PlayStation", "RPG", 2022)
        );

        // This is the critical part - we must set up the mock BEFORE calling the method under test
        when(gameRepository.findAll()).thenReturn(games);

        // Act
        List<Game> result = gameService.getAllGames();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Test Game", result.get(0).getName());
        assertEquals("Another Game", result.get(1).getName());

        // Verify the mock was called
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void testGetGameById_ExistingGame() {
        // Arrange - setup the mock to return our test game
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(testGame));

        // Act
        Game result = gameService.getGameById(GAME_ID);

        // Assert
        assertNotNull(result);
        assertEquals(GAME_ID, result.getId());
        assertEquals("Test Game", result.getName());
        assertEquals("PC", result.getPlatform());
        assertEquals("Action", result.getGenre());
        assertEquals(2023, result.getReleasedYear());

        // Verify the mock was called
        verify(gameRepository, times(1)).findById(GAME_ID);
    }

    @Test
    void testGetGameById_NonExistingGame() {
        // Arrange
        String nonExistingId = "nonExistingId";

        // Setup the mock to return empty for a non-existing ID
        when(gameRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            gameService.getGameById(nonExistingId);
        });

        // Verify exception message if needed
        assertTrue(exception.getMessage().contains(nonExistingId));

        // Verify the mock was called
        verify(gameRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void testCreateGame() {
        // Arrange
        Game newGame = new Game("New Game", "Xbox", "Strategy", 2024);
        Game savedGame = new Game("New Game", "Xbox", "Strategy", 2024);
        savedGame.setId("newGameId");

        // Setup the mock to return our saved game
        when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

        // Act
        Game result = gameService.createGame(newGame);

        // Assert
        assertNotNull(result);
        assertEquals("newGameId", result.getId());
        assertEquals("New Game", result.getName());
        assertEquals("Xbox", result.getPlatform());
        assertEquals("Strategy", result.getGenre());
        assertEquals(2024, result.getReleasedYear());

        // Verify the mock was called
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void testUpdateGame_ExistingGame() {
        // Arrange
        Game updatedGame = new Game("Updated Game", "Switch", "Adventure", 2021);

        // Need to return the test game when findById is called
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(testGame));

        // Then when save is called, return the updated game
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> {
            Game savedGame = invocation.getArgument(0);
            return savedGame; // Return the game being saved
        });

        // Act
        Game result = gameService.updateGame(GAME_ID, updatedGame);

        // Assert
        assertNotNull(result);
        assertEquals(GAME_ID, result.getId());
        assertEquals("Updated Game", result.getName());
        assertEquals("Switch", result.getPlatform());
        assertEquals("Adventure", result.getGenre());
        assertEquals(2021, result.getReleasedYear());

        // Verify the mocks were called
        verify(gameRepository, times(1)).findById(GAME_ID);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void testUpdateGame_NonExistingGame() {
        // Arrange
        String nonExistingId = "nonExistingId";
        Game updatedGame = new Game("Updated Game", "Switch", "Adventure", 2021);

        // Setup the mock to return empty for a non-existing ID
        when(gameRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            gameService.updateGame(nonExistingId, updatedGame);
        });

        // Verify exception message if needed
        assertTrue(exception.getMessage().contains(nonExistingId));

        // Verify the mock was called
        verify(gameRepository, times(1)).findById(nonExistingId);
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    void testDeleteGame_ExistingGame() {
        // Arrange
        // Need to return the test game when findById is called
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(testGame));
        doNothing().when(gameRepository).delete(any(Game.class));

        // Act
        gameService.deleteGame(GAME_ID);

        // Assert
        verify(gameRepository, times(1)).findById(GAME_ID);
        verify(gameRepository, times(1)).delete(testGame);
    }

    @Test
    void testDeleteGame_NonExistingGame() {
        // Arrange
        String nonExistingId = "nonExistingId";

        // Setup the mock to return empty for a non-existing ID
        when(gameRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            gameService.deleteGame(nonExistingId);
        });

        // Verify exception message if needed
        assertTrue(exception.getMessage().contains(nonExistingId));

        // Verify the mock was called
        verify(gameRepository, times(1)).findById(nonExistingId);
        verify(gameRepository, never()).delete(any(Game.class));
    }
}