package ro.unibuc.triplea.domain.favorites.steam.exception;


public class SteamGameFavoriteDuplicateException extends RuntimeException {
    public SteamGameFavoriteDuplicateException(String message) {
        super(message);
    }
}
