package ro.unibuc.triplea.domain.games.steam.utils;

public final class IdentifierUtil {

    private IdentifierUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static boolean isPositiveNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        return str.matches("\\d*[1-9]\\d*");
    }
}
