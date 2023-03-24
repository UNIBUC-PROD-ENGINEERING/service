package ro.unibuc.hello.converters.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateWritingConverterTest {
    private final LocalDateWritingConverter converter = new LocalDateWritingConverter();

    @Test
    public void testConvert() {
        // create a sample LocalDate
        LocalDate localDate = LocalDate.of(1970, 5, 22);

        // call the convert method and assert the result
        Date result = converter.convert(localDate);
        Assertions.assertEquals(12182400000L, result.getTime(), "Conversion result is incorrect");
    }

}