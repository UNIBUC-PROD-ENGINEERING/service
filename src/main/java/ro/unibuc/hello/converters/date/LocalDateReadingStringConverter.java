package ro.unibuc.hello.converters.date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@ReadingConverter
public class LocalDateReadingStringConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

}
