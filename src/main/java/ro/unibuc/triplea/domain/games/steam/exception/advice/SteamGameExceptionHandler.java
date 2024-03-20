package ro.unibuc.triplea.domain.games.steam.exception.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameStandardResponse;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameValidateException;

@RestControllerAdvice
@CrossOrigin
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SteamGameExceptionHandler {
    @ExceptionHandler(SteamGameNotFoundException.class)
    public ResponseEntity<SteamGameStandardResponse> handleNotFoundException(SteamGameNotFoundException e) {
        return new ResponseEntity<SteamGameStandardResponse>(new SteamGameStandardResponse("404", "Error", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SteamGameValidateException.class)
    public ResponseEntity<SteamGameStandardResponse> handleValidationException(SteamGameValidateException e) {
        return new ResponseEntity<SteamGameStandardResponse>(new SteamGameStandardResponse("400", "Error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SteamGameStandardResponse> handleException(Exception e) throws Exception {
        return new ResponseEntity<SteamGameStandardResponse>(new SteamGameStandardResponse("500", "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
