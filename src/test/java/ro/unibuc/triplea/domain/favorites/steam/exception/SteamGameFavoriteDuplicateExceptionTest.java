package ro.unibuc.triplea.domain.favorites.steam.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SteamGameFavoriteDuplicateExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Duplicate favorite Steam game";
        SteamGameFavoriteDuplicateException exception = assertThrows(SteamGameFavoriteDuplicateException.class, () -> {
            throw new SteamGameFavoriteDuplicateException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}
