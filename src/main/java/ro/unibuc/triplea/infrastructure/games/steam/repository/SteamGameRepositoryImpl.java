package ro.unibuc.triplea.infrastructure.games.steam.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.gateway.SteamGameGateway;
import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;
import ro.unibuc.triplea.domain.games.steam.repository.SteamGameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class SteamGameRepositoryImpl implements SteamGameRepository {

    private final SpringDataGameRepository springDataGameRepository;
    private final SteamGameGateway steamGameGateway;

    @Override
    public List<SteamGameResponse> findGames(Optional<Integer> count) {
        List<SteamGame> steamGames = steamGameGateway.getSteamGames(count);
        List<SteamGameResponse> responses = new ArrayList<>();
        steamGames.forEach(game -> responses.add(SteamGameResponse.builder().gameSteamId(game.getGameSteamId()).gameName(game.getGameName()).build()));
        return responses;
    }

    @Override
    public SteamGame save(SteamGame game) {
        return springDataGameRepository.save(game);
    }

    @Override
    public Optional<SteamGameResponse> findByGameSteamId(int gameSteamId) {
        Optional<SteamGame> game = steamGameGateway.getSteamGameBySteamId(gameSteamId);

        if (game.isPresent()) {
            return Optional.of(SteamGameResponse.builder().gameSteamId(game.get().getGameSteamId()).gameName(game.get().getGameName()).build());
        }

        return Optional.empty();
    }

    @Override
    public Optional<SteamGameResponse> findByGameName(String gameName) {
        Optional<SteamGame> game = steamGameGateway.getSteamGameByName(gameName);

        if (game.isPresent()) {
            return Optional.of(SteamGameResponse.builder().gameSteamId(game.get().getGameSteamId()).gameName(game.get().getGameName()).build());
        }

        return Optional.empty();
    }

}
