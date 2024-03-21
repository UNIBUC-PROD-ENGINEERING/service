package ro.unibuc.triplea.application.games.steam.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SteamGameControllerTest {

    private final SteamGameService steamGameService = Mockito.mock(SteamGameService.class);
    private final SteamGameController steamGameController = new SteamGameController(steamGameService);

    @Test
    public void testGetAllGames() {
        SteamGameResponse gameResponse = new SteamGameResponse();
        when(steamGameService.getAllGames(Optional.of(10))).thenReturn(Arrays.asList(gameResponse));

        ResponseEntity<List<SteamGameResponse>> response = steamGameController.getAllGames(Optional.of(10));

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetGameByIdentifier() {
        SteamGameResponse gameResponse = new SteamGameResponse();
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
