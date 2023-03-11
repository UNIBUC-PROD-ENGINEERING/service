package ro.unibuc.hello.converters.date;

import org.springframework.data.convert.WritingConverter;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@WritingConverter
public class LocalDateWritingConverter implements Converter<LocalDate, Date> {
    @Override
    public Date convert(LocalDate localDate) {
        return new Date(localDate.atStartOfDay(ZoneId.of("UTC")).toEpochSecond() * 1000);
    }
}
