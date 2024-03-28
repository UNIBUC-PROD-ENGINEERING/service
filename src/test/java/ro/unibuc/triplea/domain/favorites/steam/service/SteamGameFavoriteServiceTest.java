package ro.unibuc.triplea.domain.favorites.steam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ro.unibuc.triplea.application.favorites.steam.dto.request.SteamGameFavoriteRequest;
import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.favorites.steam.exception.SteamGameFavoriteDuplicateException;
import ro.unibuc.triplea.domain.favorites.steam.repository.SteamGameFavoriteRepository;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class SteamGameFavoriteServiceTest {

    @Mock
    private SteamGameFavoriteRepository steamGameFavoriteRepository;

    @Mock
    private SteamGameService steamGameService;

    @InjectMocks
    private SteamGameFavoriteService steamGameFavoriteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFavorite_Success() {
        SteamGameFavoriteRequest request = new SteamGameFavoriteRequest(123);
        UserDetails userDetails = new User("username", "password", Collections.emptyList());

        SteamGameResponse gameResponse = new SteamGameResponse();
        gameResponse.setGameName("Game Name");

        when(steamGameService.getGameBySteamId(123)).thenReturn(Optional.of(gameResponse));
        when(steamGameFavoriteRepository.save(any())).thenReturn(Optional.of(new SteamGameFavoriteResponse()));

        Optional<SteamGameFavoriteResponse> result = steamGameFavoriteService.addFavorite(request, userDetails);

        assertTrue(result.isPresent());
    }

    @Test
    void testAddFavorite_Duplicate() {
        SteamGameFavoriteRequest request = new SteamGameFavoriteRequest(123);
        UserDetails userDetails = new User("username", "password", Collections.emptyList());

        SteamGameResponse gameResponse = new SteamGameResponse();
        gameResponse.setGameName("Game Name");

        when(steamGameService.getGameBySteamId(123)).thenReturn(Optional.of(gameResponse));
        when(steamGameFavoriteRepository.save(any())).thenReturn(Optional.empty());

        assertThrows(SteamGameFavoriteDuplicateException.class, () -> steamGameFavoriteService.addFavorite(request, userDetails));
    }

    @Test
    void testAddFavorite_GameNotFound() {
        SteamGameFavoriteRequest request = new SteamGameFavoriteRequest(123);
        UserDetails userDetails = new User("username", "password", Collections.emptyList());

        when(steamGameService.getGameBySteamId(123)).thenReturn(Optional.empty());

        assertThrows(SteamGameNotFoundException.class, () -> steamGameFavoriteService.addFavorite(request, userDetails));
    }

    // Add more test cases as needed for other methods

}
