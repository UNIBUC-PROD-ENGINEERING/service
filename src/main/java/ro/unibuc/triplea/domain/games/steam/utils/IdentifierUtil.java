package ro.unibuc.triplea.domain.games.steam.utils;

public final class IdentifierUtil {

    private IdentifierUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
