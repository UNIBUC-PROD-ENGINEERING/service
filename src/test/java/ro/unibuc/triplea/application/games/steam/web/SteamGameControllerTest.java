package ro.unibuc.triplea.application.games.steam.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class SteamGameControllerTest {

    private final SteamGameService steamGameService = mock(SteamGameService.class);

    private SteamGameController steamGameController = new SteamGameController(steamGameService);

    @Test
    public void testGetAllGames() {
        int gamesCount = 2;
        List<SteamGameResponse> gameResponse = new ArrayList<>();
        gameResponse.add(SteamGameResponse.builder().gameSteamId(1).gameName("test").build());
        gameResponse.add(SteamGameResponse.builder().gameSteamId(2).gameName("test2").build());

        when(steamGameService.getAllGames(Optional.of(gamesCount))).thenReturn(gameResponse);

        ResponseEntity<List<SteamGameResponse>> response = steamGameController.getAllGames(Optional.of(gamesCount));

        assertEquals(200, response.getStatusCode().value());
        assertEquals(gameResponse, response.getBody());
        assertTrue(response.getBody().size() <= gamesCount);
    }

    @Test
    public void testGetGameByIdentifier() {
        SteamGameResponse gameResponse = SteamGameResponse.builder().gameSteamId(1).gameName("test").build();
        when(steamGameService.getGameByIdentifier("test")).thenReturn(Optional.of(gameResponse));

        ResponseEntity<?> response = steamGameController.getGameByIdentifier("test");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(gameResponse, response.getBody());
    }

    @Test
    public void testGetGameByIdentifierNotFound() {
        when(steamGameService.getGameByIdentifier("test")).thenReturn(Optional.empty());

        ResponseEntity<?> response = steamGameController.getGameByIdentifier("test");

        assertEquals(404, response.getStatusCode().value());
    }
}
