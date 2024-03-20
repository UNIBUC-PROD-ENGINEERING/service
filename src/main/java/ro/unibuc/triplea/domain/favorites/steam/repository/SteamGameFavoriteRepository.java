package ro.unibuc.triplea.domain.favorites.steam.repository;

import java.util.List;
import java.util.Optional;

import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.domain.favorites.steam.model.entity.SteamGameFavorite;

public interface SteamGameFavoriteRepository {

    Optional<List<SteamGameFavoriteResponse>> findAllByUserName(String username);

    Optional<SteamGameFavoriteResponse> save(SteamGameFavorite steamGameReview);

    
}