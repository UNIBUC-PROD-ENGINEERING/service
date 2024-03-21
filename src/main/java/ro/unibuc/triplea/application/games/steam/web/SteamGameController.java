package ro.unibuc.triplea.application.games.steam.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/games/steam")
@RequiredArgsConstructor
@Tag(name = "SteamGames", description = "Steam Game management APIs")
public class SteamGameController {

    private final SteamGameService steamGameService;

    @GetMapping("/game-list")
    public ResponseEntity<List<SteamGameResponse>> getAllGames(
            @RequestParam(required = false, name = "count") Optional<Integer> count) {
        List<SteamGameResponse> games = steamGameService.getAllGames(count);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<?> getGameByIdentifier(@PathVariable String identifier) {
        Optional<SteamGameResponse> game = steamGameService.getGameByIdentifier(identifier);

        if (game.isPresent()) {
            return ResponseEntity.ok(game.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
