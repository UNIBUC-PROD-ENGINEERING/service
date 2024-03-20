package ro.unibuc.triplea.domain.reviews.steam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.application.reviews.steam.dto.request.SteamGameReviewRequest;
import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;
import ro.unibuc.triplea.domain.games.steam.utils.IdentifierUtil;
import ro.unibuc.triplea.domain.reviews.steam.model.entity.SteamGameReview;
import ro.unibuc.triplea.domain.reviews.steam.repository.SteamGameReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SteamGameReviewService {
    private final SteamGameReviewRepository steamGameReviewRepository;
    private final SteamGameService steamGameService;
    public Optional<List<SteamGameReviewResponse>> getReviewsBySteamId(int gameSteamId) {
        return steamGameReviewRepository.findAllByGameSteamId(gameSteamId);
    }
    public Optional<List<SteamGameReviewResponse>> getReviewsByGameName(String gameName) {
        return steamGameReviewRepository.findAllByGameName(gameName);
    }
    public Optional<List<SteamGameReviewResponse>> getReviewsByGameIdentifier(String identifier) {
        if(IdentifierUtil.isNumeric(identifier)) {
            int steamId = Integer.parseInt(identifier);
            Optional<List<SteamGameReviewResponse>> steamGameReviewResponses = getReviewsBySteamId(steamId);

            if(steamGameReviewResponses.isPresent()) {
                return steamGameReviewResponses;
            }
            else {
                throw new SteamGameNotFoundException("Steam game with identifier " + identifier + " not found");
            }
        } else if(IdentifierUtil.isValidString(identifier)) {
            Optional<List<SteamGameReviewResponse>> steamGameReviewResponses = getReviewsByGameName(identifier);

            if(steamGameReviewResponses.isPresent()) {
                return steamGameReviewResponses;
            }
            else {
                throw new SteamGameNotFoundException("Steam game with identifier " + identifier + " not found");
            }
        } else {
            throw new SteamGameNotFoundException("Invalid identifier: " + identifier);
        }
    }
    public Optional<SteamGameReviewResponse> addReview(SteamGameReviewRequest game, UserDetails userDetails) {
        SteamGameReview steamGameReview = convertToSteamGameReview(game, userDetails.getUsername());
        return steamGameReviewRepository.save(steamGameReview);
    }
    public Optional<List<SteamGameReviewResponse>> getReviewsByUserName(String username) {
        return steamGameReviewRepository.findAllByUserName(username);
    }

    private SteamGameReview convertToSteamGameReview(SteamGameReviewRequest steamGameReviewRequest, String username) {
        Optional<SteamGameResponse> steamGameResponse = steamGameService.getGameBySteamId(steamGameReviewRequest.getGameSteamId());
        if(steamGameResponse.isEmpty()) {
            throw new SteamGameNotFoundException("Steam game with identifier " + steamGameReviewRequest.getGameSteamId() + " not found");
        }

        return SteamGameReview.builder()
                .gameSteamId(steamGameReviewRequest.getGameSteamId())
                .gameName(steamGameResponse.get().getGameName())
                .reviewContent(steamGameReviewRequest.getReviewContent())
                .userName(username)
                .createdBy(username)
                .build();
    }

}
