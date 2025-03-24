package ro.unibuc.hello.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class CNPValidator {
    private static final int[] CONTROL_WEIGHTS = {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9};

    public static boolean isValidCNP(String cnp) {
        if (cnp == null || cnp.length() != 13 || !cnp.matches("\\d+")) {
            return false;
        }

        int sex = Character.getNumericValue(cnp.charAt(0));
        int year = Integer.parseInt(cnp.substring(1, 3));
        int month = Integer.parseInt(cnp.substring(3, 5));
        int day = Integer.parseInt(cnp.substring(5, 7));
        int county = Integer.parseInt(cnp.substring(7, 9));
        int controlDigit = Character.getNumericValue(cnp.charAt(12));

        if (sex < 1 || sex > 9) {
            return false;
        }
        
        int fullYear = getFullYear(sex, year);
        if (fullYear == -1) {
            return false;
        }

        if (!isValidDate(fullYear, month, day)) {
            return false;
        }

        if (county < 1 || county > 52) {
            return false;
        }

        return controlDigit == calculateControlDigit(cnp);
    }

    private static int getFullYear(int sex, int year) {
        if (sex == 1 || sex == 2) return 1900 + year;
        if (sex == 3 || sex == 4) return 1800 + year;
        if (sex == 5 || sex == 6) return 2000 + year;
        if (sex == 7 || sex == 8 || sex == 9) return 2000 + year; 
        return -1;
    }

    private static boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.parse(String.format("%04d-%02d-%02d", year, month, day),
                    DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static int calculateControlDigit(String cnp) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnp.charAt(i)) * CONTROL_WEIGHTS[i];
        }
        int remainder = sum % 11;
        return (remainder == 10) ? 1 : remainder;
    }

    public static void main(String[] args) {
        String testCNP = "1971122334456";
        System.out.println("CNP " + testCNP + " este valid? " + isValidCNP(testCNP));
    }
}
