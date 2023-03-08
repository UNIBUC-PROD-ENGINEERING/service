package ro.unibuc.hello.converters.date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@ReadingConverter
public class LocalDateReadingConverter implements Converter<Date, LocalDate> {

    @Override
    public LocalDate convert(Date date) {
        return date.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
    }

}
