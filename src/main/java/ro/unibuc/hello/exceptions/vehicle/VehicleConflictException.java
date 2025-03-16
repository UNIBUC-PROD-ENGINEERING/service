package ro.unibuc.hello.exceptions.vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VehicleConflictException extends RuntimeException {
    public VehicleConflictException(String message) {
        super(message);
    }
}
