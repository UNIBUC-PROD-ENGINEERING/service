package ro.unibuc.triplea.application.reviews.steam.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.triplea.application.reviews.steam.dto.request.SteamGameReviewRequest;
import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.reviews.steam.service.SteamGameReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews/steam")
@RequiredArgsConstructor
@Tag(name = "SteamGameReviews", description = "Steam Game Review management APIs")
public class SteamGameReviewController {

    private final SteamGameReviewService steamGameReviewService;

    @GetMapping("/game-id/{gameId}")
    public ResponseEntity<?> getReviewsBySteamId(@PathVariable String gameId) {
        Optional<List<SteamGameReviewResponse>> reviews = steamGameReviewService.getReviewsByGameIdentifier(gameId);
        if (reviews.isPresent()) {
            return ResponseEntity.ok(reviews.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user-name/{userName}")
    public ResponseEntity<?> getReviewsByUserId(@PathVariable String userName) {
        Optional<List<SteamGameReviewResponse>> reviews = steamGameReviewService.getReviewsByUserName(userName);
        if (reviews.isPresent()) {
            return ResponseEntity.ok(reviews.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody SteamGameReviewRequest review, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<SteamGameReviewResponse> addedReview = steamGameReviewService.addReview(review, userDetails);
        if (addedReview.isPresent()) {
            return ResponseEntity.ok(addedReview.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
