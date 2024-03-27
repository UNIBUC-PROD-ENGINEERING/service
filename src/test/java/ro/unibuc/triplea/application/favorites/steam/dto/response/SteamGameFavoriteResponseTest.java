package ro.unibuc.triplea.application.favorites.steam.dto.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SteamGameFavoriteResponseTest {

    @Test
    void testConstructorAndGettersSetters() {
        // Given
        String userName = "john_doe";
        int gameSteamId = 12345;
        String gameName = "Sample Game";

        // When
        SteamGameFavoriteResponse response = new SteamGameFavoriteResponse(userName, gameSteamId, gameName);

        // Then
        assertEquals(userName, response.getUserName());
        assertEquals(gameSteamId, response.getGameSteamId());
        assertEquals(gameName, response.getGameName());

        // Modify and check setters
        String newUserName = "jane_doe";
        int newSteamId = 54321;
        String newGameName = "New Game";

        response.setUserName(newUserName);
        response.setGameSteamId(newSteamId);
        response.setGameName(newGameName);

        assertEquals(newUserName, response.getUserName());
        assertEquals(newSteamId, response.getGameSteamId());
        assertEquals(newGameName, response.getGameName());
    }
}
