package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.unibuc.hello.data.Game;
import ro.unibuc.hello.data.Rent;
import ro.unibuc.hello.data.RentRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RentServiceTest {

    @Mock
    private RentRepository rentRepository;

    @Mock
    private GameService gameService;

    @InjectMocks
    private RentService rentService;

    private static final String USER_ID = "user123";
    private static final String GAME_ID = "game456";
    private static final String RENT_ID = "rent789";

    private Rent testRent;
    private Game testGame;

    @BeforeEach
    void setUp() {
        // Create test data
        testRent = new Rent(USER_ID, GAME_ID);
        testRent.setId(RENT_ID);
        testRent.setRentDate(LocalDateTime.now().minusDays(1));
        testRent.setReturned(false);

        testGame = new Game("Test Game", "PC", "Action", 2023);
        testGame.setId(GAME_ID);
    }

    @Test
    void testGetAllRents() {
        // Arrange
        List<Rent> rents = Arrays.asList(
                testRent,
                new Rent("user456", "game789")
        );
        rents.get(1).setId("rent321");

        // Set up the mock
        when(rentRepository.findAll()).thenReturn(rents);

        // Act
        List<Rent> result = rentService.getAllRents();

        // Assert
        assertEquals(2, result.size());
        assertEquals(USER_ID, result.get(0).getUserId());
        assertEquals(GAME_ID, result.get(0).getGameId());

        // Verify the mock was called
        verify(rentRepository, times(1)).findAll();
    }

    @Test
    void testGetRentById_ExistingRent() {
        // Arrange
        when(rentRepository.findById(RENT_ID)).thenReturn(Optional.of(testRent));

        // Act
        Rent result = rentService.getRentById(RENT_ID);

        // Assert
        assertNotNull(result);
        assertEquals(RENT_ID, result.getId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(GAME_ID, result.getGameId());

        // Verify the mock was called
        verify(rentRepository, times(1)).findById(RENT_ID);
    }

    @Test
    void testGetRentById_NonExistingRent() {
        // Arrange
        String nonExistingId = "nonExistingId";
        when(rentRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            rentService.getRentById(nonExistingId);
        });

        // Verify the message
        assertTrue(exception.getMessage().contains(nonExistingId));

        // Verify the mock was called
        verify(rentRepository, times(1)).findById(nonExistingId);
    }

    @Test
    void testGetRentsByUserId() {
        // Arrange
        Rent rent1 = testRent;
        Rent rent2 = new Rent(USER_ID, "game789");
        rent2.setId("rent321");

        List<Rent> userRents = Arrays.asList(rent1, rent2);

        // Set up the mock
        when(rentRepository.findByUserId(USER_ID)).thenReturn(userRents);

        // Act
        List<Rent> result = rentService.getRentsByUserId(USER_ID);

        // Assert
        assertEquals(2, result.size());
        assertEquals(USER_ID, result.get(0).getUserId());
        assertEquals(GAME_ID, result.get(0).getGameId());

        // Verify the mock was called
        verify(rentRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    void testGetRentsByGameId() {
        // Arrange
        Rent rent1 = testRent;
        Rent rent2 = new Rent("user456", GAME_ID);
        rent2.setId("rent321");

        List<Rent> gameRents = Arrays.asList(rent1, rent2);

        // Set up the mock
        when(rentRepository.findByGameId(GAME_ID)).thenReturn(gameRents);

        // Act
        List<Rent> result = rentService.getRentsByGameId(GAME_ID);

        // Assert
        assertEquals(2, result.size());
        assertEquals(GAME_ID, result.get(0).getGameId());
        assertEquals(USER_ID, result.get(0).getUserId());

        // Verify the mock was called
        verify(rentRepository, times(1)).findByGameId(GAME_ID);
    }

    @Test
    void testRentGame_Success() {
        // Arrange
        // Mock the behavior to check if game exists
        when(gameService.getGameById(GAME_ID)).thenReturn(testGame);

        // Mock that there are no active rentals
        when(rentRepository.findByUserIdAndGameIdAndIsReturnedFalse(USER_ID, GAME_ID))
                .thenReturn(Collections.emptyList());

        // Mock the saving behavior
        Rent savedRent = new Rent(USER_ID, GAME_ID);
        savedRent.setId(RENT_ID);
        savedRent.setRentDate(LocalDateTime.now());
        savedRent.setReturned(false);

        when(rentRepository.save(any(Rent.class))).thenReturn(savedRent);

        // Act
        Rent result = rentService.rentGame(USER_ID, GAME_ID);

        // Assert
        assertNotNull(result);
        assertEquals(RENT_ID, result.getId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(GAME_ID, result.getGameId());
        assertFalse(result.isReturned());

        // Verify the mocks were called
        verify(gameService, times(1)).getGameById(GAME_ID);
        verify(rentRepository, times(1)).findByUserIdAndGameIdAndIsReturnedFalse(USER_ID, GAME_ID);
        verify(rentRepository, times(1)).save(any(Rent.class));
    }

    @Test
    void testRentGame_AlreadyRented() {
        // Arrange
        // Mock the behavior to check if game exists
        when(gameService.getGameById(GAME_ID)).thenReturn(testGame);

        // Mock that the game is already rented by this user
        when(rentRepository.findByUserIdAndGameIdAndIsReturnedFalse(USER_ID, GAME_ID))
                .thenReturn(Collections.singletonList(testRent));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            rentService.rentGame(USER_ID, GAME_ID);
        });

        // Verify exception message
        assertTrue(exception.getMessage().contains("already rented"));

        // Verify the mocks were called
        verify(gameService, times(1)).getGameById(GAME_ID);
        verify(rentRepository, times(1)).findByUserIdAndGameIdAndIsReturnedFalse(USER_ID, GAME_ID);
        verify(rentRepository, never()).save(any(Rent.class));
    }

    @Test
    void testReturnGame_Success() {
        // Arrange
        // Mock that the game is rented by this user
        when(rentRepository.findByUserIdAndGameIdAndIsReturnedFalse(USER_ID, GAME_ID))
                .thenReturn(Collections.singletonList(testRent));

        // Mock saving the returned rent
        Rent returnedRent = new Rent(USER_ID, GAME_ID);
        returnedRent.setId(RENT_ID);
        returnedRent.setRentDate(testRent.getRentDate());
        returnedRent.setReturnDate(LocalDateTime.now());
        returnedRent.setReturned(true);

        when(rentRepository.save(any(Rent.class))).thenReturn(returnedRent);

        // Act
        Rent result = rentService.returnGame(USER_ID, GAME_ID);

        // Assert
        assertNotNull(result);
        assertEquals(RENT_ID, result.getId());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(GAME_ID, result.getGameId());
        assertTrue(result.isReturned());
        assertNotNull(result.getReturnDate());

        // Verify the mocks were called
        verify(rentRepository, times(1)).findByUserIdAndGameIdAndIsReturnedFalse(USER_ID, GAME_ID);
        verify(rentRepository, times(1)).save(any(Rent.class));
    }

    @Test
    void testReturnGame_NoActiveRental() {
        // Arrange
        // Mock that there are no active rentals
        when(rentRepository.findByUserIdAndGameIdAndIsReturnedFalse(USER_ID, GAME_ID))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            rentService.returnGame(USER_ID, GAME_ID);
        });

        // Verify exception message
        assertTrue(exception.getMessage().contains("No active rental found"));

        // Verify the mock was called
        verify(rentRepository, times(1)).findByUserIdAndGameIdAndIsReturnedFalse(USER_ID, GAME_ID);
        verify(rentRepository, never()).save(any(Rent.class));
    }
}