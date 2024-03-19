package ro.unibuc.triplea.domain.games.steam.repository;

import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;

import java.util.List;
import java.util.Optional;

public interface SteamGameRepository {

    Optional<SteamGameResponse> findByGameSteamId(int gameSteamId);
    Optional<SteamGameResponse> findByGameName(String gameName);
    List<SteamGameResponse> findGames(Optional<Integer> count);

    SteamGame save(SteamGame game);
}
