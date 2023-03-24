package ro.unibuc.hello.converters.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateReadingConverterTest {

    private final LocalDateReadingConverter converter = new LocalDateReadingConverter();

    @Test
    public void testConvert() {
        Date date = new Date(12233455678L); //date in milliseconds

        // this method verifies if "convert" method converts LocalDate from Date
        LocalDate result = converter.convert(date);
        Assertions.assertEquals(LocalDate.of(1970, 5, 22), result, "Conversion result is incorrect");
    }
}