package ro.unibuc.triplea.domain.favorites.steam.exception.advice;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameStandardResponse;
import ro.unibuc.triplea.domain.favorites.steam.exception.SteamGameFavoriteDuplicateException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class SteamGameFavoriteExceptionHandlerTest {

    @Mock
    private SteamGameFavoriteDuplicateException steamGameFavoriteDuplicateException;

    @InjectMocks
    private SteamGameFavoriteExceptionHandler steamGameFavoriteExceptionHandler;

    @Test
    void handleDuplicateException() {
        // Mocking the exception message
        String exceptionMessage = "Duplicate favorite Steam game";
        steamGameFavoriteDuplicateException = mock(SteamGameFavoriteDuplicateException.class);
        Mockito.when(steamGameFavoriteDuplicateException.getMessage()).thenReturn(exceptionMessage);

        // Creating an instance of the class to test
        SteamGameFavoriteExceptionHandler handler = new SteamGameFavoriteExceptionHandler();

        // Invoking the method under test
        ResponseEntity<SteamGameStandardResponse> responseEntity = handler.handleDuplicateException(steamGameFavoriteDuplicateException);

        // Verifying the response status code and body
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("409", responseEntity.getBody().getCode());
        assertEquals("Error", responseEntity.getBody().getMessage());
        assertEquals(exceptionMessage, responseEntity.getBody().getData());
    }
}
