package ro.unibuc.triplea.infrastructure.favorites.steam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.unibuc.triplea.domain.favorites.steam.model.entity.SteamGameFavorite;

import java.util.List;

public interface SpringDataSteamGameFavoriteRepository extends JpaRepository<SteamGameFavorite, Integer> {
    List<SteamGameFavorite> findAllByUserName(String userName);

    SteamGameFavorite findByUserNameAndGameSteamId(String userName, int gameSteamId);
}
