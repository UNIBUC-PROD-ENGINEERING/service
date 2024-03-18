package ro.unibuc.hello.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus(HttpStatus.NOT_FOUND)
public class DuplicateReadingRecordException extends RuntimeException {
    public DuplicateReadingRecordException(String message) {
        super(message);
    }
}