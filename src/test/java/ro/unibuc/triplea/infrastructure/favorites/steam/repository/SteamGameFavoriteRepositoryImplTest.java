package ro.unibuc.triplea.infrastructure.favorites.steam.repository;

import org.junit.jupiter.api.Test;
import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.domain.favorites.steam.model.entity.SteamGameFavorite;
import ro.unibuc.triplea.infrastructure.auth.repository.SpringDataUserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class SteamGameFavoriteRepositoryImplTest {

    private SpringDataSteamGameFavoriteRepository mockSteamGameFavoriteRepository = mock(SpringDataSteamGameFavoriteRepository.class);

    private SpringDataUserRepository mockUserRepository = mock(SpringDataUserRepository.class);

    private SteamGameFavoriteRepositoryImpl steamGameFavoriteRepository = new SteamGameFavoriteRepositoryImpl(mockSteamGameFavoriteRepository, mockUserRepository);

    @Test
    void testFindAllByUserName() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        SteamGameFavorite favorite1 = new SteamGameFavorite();
        favorite1.setUserName(username);
        favorite1.setGameSteamId(1);

        SteamGameFavorite favorite2 = new SteamGameFavorite();
        favorite2.setUserName(username);
        favorite2.setGameSteamId(2);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(mockSteamGameFavoriteRepository.findAllByUserName(username)).thenReturn(Arrays.asList(favorite1, favorite2));

        Optional<List<SteamGameFavoriteResponse>> result = steamGameFavoriteRepository.findAllByUserName(username);
        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertEquals(favorite1.getGameSteamId(), result.get().get(0).getGameSteamId());
        assertEquals(favorite2.getGameSteamId(), result.get().get(1).getGameSteamId());
    }

    @Test
    void testSave() {
        SteamGameFavorite favorite = new SteamGameFavorite();
        favorite.setUserName("testUser");
        favorite.setGameSteamId(1);

        when(mockSteamGameFavoriteRepository.findByUserNameAndGameSteamId(favorite.getUserName(), favorite.getGameSteamId())).thenReturn(Optional.empty());
        when(mockSteamGameFavoriteRepository.save(favorite)).thenReturn(favorite);

        Optional<SteamGameFavoriteResponse> result = steamGameFavoriteRepository.save(favorite);
        assertTrue(result.isPresent());
        assertEquals(favorite.getGameSteamId(), result.get().getGameSteamId());
    }
}
