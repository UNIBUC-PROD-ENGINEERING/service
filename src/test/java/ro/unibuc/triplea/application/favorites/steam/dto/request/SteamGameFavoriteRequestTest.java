package ro.unibuc.triplea.application.favorites.steam.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SteamGameFavoriteRequestTest {

    @Test
    void testConstructorAndGetterSetter() {
        // Given
        int gameSteamId = 12345;

        // When
        SteamGameFavoriteRequest request = new SteamGameFavoriteRequest(gameSteamId);

        // Then
        assertEquals(gameSteamId, request.getGameSteamId());

        // Modify and check setter
        int newSteamId = 54321;
        request.setGameSteamId(newSteamId);
        assertEquals(newSteamId, request.getGameSteamId());
    }
}
