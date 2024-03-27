package ro.unibuc.triplea.domain.favorites.steam.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SteamGameFavoriteTest {

    @Test
    public void testGameSteamId() {
        SteamGameFavorite steamGameFavorite = new SteamGameFavorite();
        steamGameFavorite.setGameSteamId(123456);
        assertEquals(123456, steamGameFavorite.getGameSteamId());
    }
}
