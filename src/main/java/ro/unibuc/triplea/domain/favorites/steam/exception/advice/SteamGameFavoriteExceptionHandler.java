package ro.unibuc.triplea.domain.favorites.steam.exception.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameStandardResponse;
import ro.unibuc.triplea.domain.favorites.steam.exception.SteamGameFavoriteDuplicateException;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;

@RestControllerAdvice
@CrossOrigin
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SteamGameFavoriteExceptionHandler {
    @ExceptionHandler(SteamGameFavoriteDuplicateException.class)
    public ResponseEntity<SteamGameStandardResponse> handleDuplicateException(SteamGameFavoriteDuplicateException e) {
        return new ResponseEntity<SteamGameStandardResponse>(new SteamGameStandardResponse("409", "Error", e.getMessage()), HttpStatus.CONFLICT);
    }
}