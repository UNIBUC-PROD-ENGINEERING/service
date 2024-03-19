package ro.unibuc.triplea.domain.games.steam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameValidateException;
import ro.unibuc.triplea.domain.games.steam.repository.SteamGameRepository;
import ro.unibuc.triplea.domain.games.steam.utils.IdentifierUtil;

@Service
@RequiredArgsConstructor
public class SteamGameService {
    
    private final SteamGameRepository steamGameRepository;

    public List<SteamGameResponse> getAllGames(Optional<Integer> count) {
        return steamGameRepository.findGames(count);
    }

    public Optional<SteamGameResponse> getGameBySteamId(int gameSteamId) {
        return steamGameRepository.findByGameSteamId(gameSteamId);
    }

    public Optional<SteamGameResponse> getGameByName(String gameName) {
        return steamGameRepository.findByGameName(gameName);
    }

    public Optional<SteamGameResponse> getGameByIdentifier(String identifier) {
        if (IdentifierUtil.isNumeric(identifier)) {
            int steamId = Integer.parseInt(identifier);
            Optional<SteamGameResponse> game = getGameBySteamId(steamId);

            if (game.isPresent()) {
                return game;
            }
            else {
                throw new SteamGameNotFoundException("Steam game with identifier " + identifier + " not found");
            }
        } else if (IdentifierUtil.isValidString(identifier)) {
            Optional<SteamGameResponse> game = getGameByName(identifier);

            if (game.isPresent()) {
                return game;
            }
            else {
                throw new SteamGameNotFoundException("Steam game with identifier " + identifier + " not found");
            }
        } else {
            throw new SteamGameValidateException("Invalid identifier: " + identifier);
        }
    }
}
