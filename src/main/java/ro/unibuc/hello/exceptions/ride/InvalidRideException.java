package ro.unibuc.hello.exceptions.ride;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRideException extends RuntimeException {
    public InvalidRideException(String message) {
        super(message);
    }
}
