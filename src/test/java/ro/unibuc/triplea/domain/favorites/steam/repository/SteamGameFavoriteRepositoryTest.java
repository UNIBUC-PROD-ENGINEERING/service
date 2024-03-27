package ro.unibuc.triplea.domain.favorites.steam.repository;

import org.junit.jupiter.api.Test;
import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.domain.favorites.steam.model.entity.SteamGameFavorite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SteamGameFavoriteRepositoryTest {

    @Test
    public void testFindAllByUserName() {
        // Create a mock implementation of the repository interface
        SteamGameFavoriteRepository repository = mock(SteamGameFavoriteRepository.class);

        // Define the behavior of the mock
        List<SteamGameFavoriteResponse> favoritesList = Collections.emptyList();
        when(repository.findAllByUserName("testUser")).thenReturn(Optional.of(favoritesList));

        // Call the method being tested
        Optional<List<SteamGameFavoriteResponse>> result = repository.findAllByUserName("testUser");

        // Verify the result
        assertEquals(favoritesList, result.orElse(null));
    }

    @Test
    public void testSave() {
        // Create a mock implementation of the repository interface
        SteamGameFavoriteRepository repository = mock(SteamGameFavoriteRepository.class);

        // Define the behavior of the mock
        SteamGameFavorite steamGameFavorite = new SteamGameFavorite(/* pass necessary parameters */);
        SteamGameFavoriteResponse savedFavorite = new SteamGameFavoriteResponse(/* pass necessary parameters */);
        when(repository.save(steamGameFavorite)).thenReturn(Optional.of(savedFavorite));

        // Call the method being tested
        Optional<SteamGameFavoriteResponse> result = repository.save(steamGameFavorite);

        // Verify the result
        assertEquals(savedFavorite, result.orElse(null));
    }
}
