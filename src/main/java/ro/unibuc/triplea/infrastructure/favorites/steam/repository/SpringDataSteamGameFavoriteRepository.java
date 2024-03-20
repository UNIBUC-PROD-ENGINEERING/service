package ro.unibuc.triplea.infrastructure.favorites.steam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.unibuc.triplea.domain.favorites.steam.model.entity.SteamGameFavorite;
public interface SpringDataSteamGameFavoriteRepository extends JpaRepository<SteamGameFavorite, Integer> {
    List<SteamGameFavorite> findAllByUserName(String userName);
    SteamGameFavorite findByUserNameAndGameSteamId(String userName, int gameSteamId);
}
