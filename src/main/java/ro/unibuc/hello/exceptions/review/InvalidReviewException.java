package ro.unibuc.hello.exceptions.review;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidReviewException extends RuntimeException {
    public InvalidReviewException(String message) {
        super(message);
    }
}
