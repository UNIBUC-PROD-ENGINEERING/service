package ro.unibuc.triplea.domain.games.steam.exception;

public class SteamGameNotFoundException extends RuntimeException {
    public SteamGameNotFoundException(String message) {
        super(message);
    }
}
