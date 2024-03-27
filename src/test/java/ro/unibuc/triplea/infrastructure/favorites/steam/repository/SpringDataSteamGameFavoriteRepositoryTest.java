package ro.unibuc.triplea.infrastructure.favorites.steam.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ro.unibuc.triplea.domain.favorites.steam.model.entity.SteamGameFavorite;
import ro.unibuc.triplea.infrastructure.favorites.steam.repository.SpringDataSteamGameFavoriteRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SpringDataSteamGameFavoriteRepositoryTest {

    @Autowired
    private SpringDataSteamGameFavoriteRepository repository;

    @Test
    void testFindAllByUserName() {
        // Given
        String userName = "testUser";
        SteamGameFavorite game1 = new SteamGameFavorite();
        SteamGameFavorite game2 = new SteamGameFavorite();
        repository.save(game1);
        repository.save(game2);

        // When
        List<SteamGameFavorite> favorites = repository.findAllByUserName(userName);

        // Then
        assertEquals(2, favorites.size());
    }

    @Test
    void testFindByUserNameAndGameSteamId() {
        // Given
        String userName = "testUser";
        int gameSteamId = 12345;
        SteamGameFavorite game = new SteamGameFavorite();
        repository.save(game);

        // When
        Optional<SteamGameFavorite> optionalFavorite = repository.findByUserNameAndGameSteamId(userName, gameSteamId);

        // Then
        assertTrue(optionalFavorite.isPresent());
        SteamGameFavorite foundGame = optionalFavorite.get();
        assertEquals(userName, foundGame.getUserName());
        assertEquals(gameSteamId, foundGame.getGameSteamId());
    }
}
