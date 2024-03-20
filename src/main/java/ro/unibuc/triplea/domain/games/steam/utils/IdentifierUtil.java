package ro.unibuc.triplea.domain.games.steam.utils;

import java.util.regex.Pattern;

public final class IdentifierUtil {

    private IdentifierUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public static boolean isValidString(String str) {
        return Pattern.matches("^([^\\\\u4E00-\\\\u9FFF]*?)(?:[\\\\p{L}0-9' .:~\\\\u4E00-\\\\u9FFF].*)?$", str);
    }
}
