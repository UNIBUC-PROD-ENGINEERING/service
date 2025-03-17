package ro.unibuc.hello.exceptions.rideBooking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RideBookingConflictException extends RuntimeException {
    public RideBookingConflictException(String message) {
        super(message);
    }
}


