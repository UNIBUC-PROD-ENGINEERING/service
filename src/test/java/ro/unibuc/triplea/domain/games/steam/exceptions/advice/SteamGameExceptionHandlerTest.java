package ro.unibuc.triplea.domain.games.steam.exceptions.advice;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameStandardResponse;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.exception.advice.SteamGameExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SteamGameExceptionHandlerTest {

    private SteamGameExceptionHandler exceptionHandler = new SteamGameExceptionHandler();

    @Test
    public void testHandleNotFoundException() {
        SteamGameNotFoundException exception = new SteamGameNotFoundException("Game not found");

        ResponseEntity<SteamGameStandardResponse> response = exceptionHandler.handleNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("404", response.getBody().getCode());
        assertEquals("Error", response.getBody().getMessage());
        assertEquals("Game not found", response.getBody().getData());
    }

    @Test
    public void testHandleException() throws Exception {
        Exception exception = new Exception("Internal server error");

        ResponseEntity<SteamGameStandardResponse> response = exceptionHandler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("500", response.getBody().getCode());
        assertEquals("Error", response.getBody().getMessage());
        assertEquals("Internal server error", response.getBody().getData());
    }
}
