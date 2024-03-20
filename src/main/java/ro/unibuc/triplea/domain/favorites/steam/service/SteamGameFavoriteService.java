package ro.unibuc.triplea.domain.favorites.steam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.unibuc.triplea.application.favorites.steam.dto.request.SteamGameFavoriteRequest;
import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.favorites.steam.exception.SteamGameFavoriteDuplicateException;
import ro.unibuc.triplea.domain.favorites.steam.model.entity.SteamGameFavorite;
import ro.unibuc.triplea.domain.favorites.steam.repository.SteamGameFavoriteRepository;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class SteamGameFavoriteService {
    private final SteamGameFavoriteRepository steamGameFavoriteRepository;
    private final SteamGameService steamGameService;

    public Optional<SteamGameFavoriteResponse> addFavorite(SteamGameFavoriteRequest favorite, UserDetails userDetails) {
        SteamGameFavorite steamGameFavorite = convertToSteamGameFavorite(favorite, userDetails.getUsername());
        Optional<SteamGameFavoriteResponse> savedFavorite = steamGameFavoriteRepository.save(steamGameFavorite);
        if (savedFavorite.isEmpty()) {
            throw new SteamGameFavoriteDuplicateException("Steam game favorite with identifier " + favorite.getGameSteamId() + " and user " + userDetails.getUsername() + " already exists");
        }
        return savedFavorite;
    }

    public Optional<List<SteamGameFavoriteResponse>> getFavoritesByUserName(String username) {
        return steamGameFavoriteRepository.findAllByUserName(username);
    }

    private SteamGameFavorite convertToSteamGameFavorite(SteamGameFavoriteRequest steamGameFavoriteRequest, String username) {
        Optional<SteamGameResponse> steamGameResponse = steamGameService.getGameBySteamId(steamGameFavoriteRequest.getGameSteamId());
        if (steamGameResponse.isEmpty()) {
            throw new SteamGameNotFoundException("Steam game with identifier " + steamGameFavoriteRequest.getGameSteamId() + " not found");
        }

        return SteamGameFavorite.builder()
                .gameSteamId(steamGameFavoriteRequest.getGameSteamId())
                .gameName(steamGameResponse.get().getGameName())
                .userName(username)
                .createdBy(username)
                .build();
    }

}
