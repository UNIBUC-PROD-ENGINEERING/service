package ro.unibuc.triplea.domain.games.steam.exception;

public class SteamGameValidateException extends RuntimeException {
    public SteamGameValidateException(String message) {
        super(message);
    }
}
