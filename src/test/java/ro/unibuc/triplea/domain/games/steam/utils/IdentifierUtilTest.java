package ro.unibuc.triplea.domain.games.steam.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IdentifierUtilTest {

    @Test
    public void testIsPositiveNumeric_ValidNumericString() {
        String numericString = "12345";
        assertTrue(IdentifierUtil.isPositiveNumeric(numericString));
    }

    @Test
    public void testIsPositiveNumeric_InvalidNumericString() {
        String nonNumericString = "abc123";
        assertFalse(IdentifierUtil.isPositiveNumeric(nonNumericString));
    }

    @Test
    public void testIsPositiveNumeric_NegativeNumber() {
        String negativeNumber = "-12345";
        assertFalse(IdentifierUtil.isPositiveNumeric(negativeNumber));
    }

    @Test
    public void testIsPositiveNumeric_LeadingSpaces() {
        String leadingSpaces = "   12345";
        assertFalse(IdentifierUtil.isPositiveNumeric(leadingSpaces));
    }

    @Test
    public void testIsNumeric_TrailingSpaces() {
        String trailingSpaces = "12345   ";
        assertFalse(IdentifierUtil.isPositiveNumeric(trailingSpaces));
    }

    @Test
    public void testIsPositiveNumeric_LeadingAndTrailingSpaces() {
        String spacesAroundNumber = "  12345   ";
        assertFalse(IdentifierUtil.isPositiveNumeric(spacesAroundNumber));
    }

    @Test
    public void testIsPositiveNumeric_EmptyString() {
        String emptyString = "";
        assertFalse(IdentifierUtil.isPositiveNumeric(emptyString));
    }

    @Test
    public void testIsPositiveNumeric_NullString() {
        String nullString = null;
        assertFalse(IdentifierUtil.isPositiveNumeric(nullString));
    }

}