package ro.unibuc.hello.exceptions.ride;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RideConflictException extends RuntimeException {
    public RideConflictException(String message) {
        super(message);
    }
}
