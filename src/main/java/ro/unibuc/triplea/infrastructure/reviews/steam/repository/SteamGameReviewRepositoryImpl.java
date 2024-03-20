package ro.unibuc.triplea.infrastructure.reviews.steam.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.domain.games.steam.gateway.SteamGameGateway;
import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;
import ro.unibuc.triplea.domain.reviews.steam.model.entity.SteamGameReview;
import ro.unibuc.triplea.domain.reviews.steam.repository.SteamGameReviewRepository;
import ro.unibuc.triplea.infrastructure.auth.repository.SpringDataUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@Primary
@RequiredArgsConstructor
public class SteamGameReviewRepositoryImpl implements SteamGameReviewRepository {

    private final SpringDataSteamGameReviewRepository springDataSteamGameReviewRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final SteamGameGateway steamGameGateway;

    @Override
    public Optional<List<SteamGameReviewResponse>> findAllByGameSteamId(int gameSteamId) {
        SteamGame game = steamGameGateway.getSteamGameBySteamId(gameSteamId).orElse(null);
        if (game == null) {
            return Optional.empty();
        }


        List<SteamGameReview> steamGameReviews = springDataSteamGameReviewRepository.findAllByGameSteamId(gameSteamId);

        return Optional.of(steamGameReviews.stream()
                .map(steamGameReview -> SteamGameReviewResponse.builder()
                        .gameSteamId(steamGameReview.getGameSteamId())
                        .gameName(steamGameReview.getGameName())
                        .userName(steamGameReview.getUserName())
                        .reviewContent(steamGameReview.getReviewContent())
                        .build())
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<SteamGameReviewResponse>> findAllByGameName(String gameName) {
        SteamGame game = steamGameGateway.getSteamGameByName(gameName).orElse(null);
        if (game == null) {
            return Optional.empty();
        }

        List<SteamGameReview> steamGameReviews = springDataSteamGameReviewRepository.findAllByGameSteamId(game.getGameSteamId());
        return Optional.of(steamGameReviews.stream()
                .map(steamGameReview -> SteamGameReviewResponse.builder()
                        .gameSteamId(steamGameReview.getGameSteamId())
                        .gameName(steamGameReview.getGameName())
                        .userName(steamGameReview.getUserName())
                        .reviewContent(steamGameReview.getReviewContent())
                        .build())
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<SteamGameReviewResponse>> findAllByUserName(String userName) {
        User user = springDataUserRepository.findByUsername(userName).orElse(null);
        if (user == null) {
            return Optional.empty();
        }

        List<SteamGameReview> steamGameReviews = springDataSteamGameReviewRepository.findAllByUserName(userName);
        return Optional.of(steamGameReviews.stream()
                .map(steamGameReview -> SteamGameReviewResponse.builder()
                        .gameSteamId(steamGameReview.getGameSteamId())
                        .gameName(steamGameReview.getGameName())
                        .userName(steamGameReview.getUserName())
                        .reviewContent(steamGameReview.getReviewContent())
                        .build())
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<SteamGameReviewResponse> save(SteamGameReview steamGameReview) {
        SteamGame game = steamGameGateway.getSteamGameBySteamId(steamGameReview.getGameSteamId()).orElse(null);
        if (game == null) {
            return Optional.empty();
        }

        SteamGameReview savedSteamGameReview = springDataSteamGameReviewRepository.save(steamGameReview);
        return Optional.of(SteamGameReviewResponse.builder()
                .gameSteamId(savedSteamGameReview.getGameSteamId())
                .gameName(savedSteamGameReview.getGameName())
                .userName(savedSteamGameReview.getUserName())
                .reviewContent(savedSteamGameReview.getReviewContent())
                .build());
    }
}
