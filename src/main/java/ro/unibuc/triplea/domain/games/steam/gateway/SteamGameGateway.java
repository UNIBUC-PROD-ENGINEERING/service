package ro.unibuc.triplea.domain.games.steam.gateway;

import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;

import java.util.List;
import java.util.Optional;

public interface SteamGameGateway {
    List<SteamGame> getSteamGames(Optional<Integer> count);

    Optional<SteamGame> getSteamGameBySteamId(int gameSteamId);

    Optional<SteamGame> getSteamGameByName(String gameName);
}
