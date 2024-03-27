package ro.unibuc.triplea.domain.favorites.steam.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameFavoriteTest {

    @Test
    public void testId() {
        GameFavorite gameFavorite = new GameFavorite();
        gameFavorite.setId(1);
        assertEquals(1, gameFavorite.getId());
    }

    @Test
    public void testUserName() {
        GameFavorite gameFavorite = new GameFavorite();
        gameFavorite.setUserName("testUser");
        assertEquals("testUser", gameFavorite.getUserName());
    }

    @Test
    public void testGameName() {
        GameFavorite gameFavorite = new GameFavorite();
        gameFavorite.setGameName("testGame");
        assertEquals("testGame", gameFavorite.getGameName());
    }
}
