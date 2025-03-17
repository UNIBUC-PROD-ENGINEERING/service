package ro.unibuc.hello.exceptions.rideBooking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRideBookingException extends RuntimeException {
    public InvalidRideBookingException(String message) {
        super(message);
    }
}

