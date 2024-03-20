package ro.unibuc.triplea.domain.reviews.steam.repository;

import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.reviews.steam.model.entity.SteamGameReview;

import java.util.List;
import java.util.Optional;

public interface SteamGameReviewRepository {
    Optional<List<SteamGameReviewResponse>> findAllByGameSteamId(int gameSteamId);
    Optional<List<SteamGameReviewResponse>> findAllByGameName(String gameName);
    Optional<List<SteamGameReviewResponse>> findAllByUserName(String userName);

    Optional<SteamGameReviewResponse> save(SteamGameReview game);

}
