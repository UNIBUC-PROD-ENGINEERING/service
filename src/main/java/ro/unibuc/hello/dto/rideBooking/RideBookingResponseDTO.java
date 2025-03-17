package ro.unibuc.hello.dto.rideBooking;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import ro.unibuc.hello.enums.RideBookingStatus;
import ro.unibuc.hello.model.RideBooking;

public class RideBookingResponseDTO {
    
    private String passengerFullName;
    private String rideId;
    private RideBookingStatus bookingStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant createdAt;

    public RideBookingResponseDTO() {}

    public RideBookingResponseDTO(String rideId, RideBookingStatus bookingStatus, Instant createdAt)
    {
        this.rideId = rideId;
        this.bookingStatus = bookingStatus;
        this.createdAt = createdAt;
    }

    public static RideBookingResponseDTO toDTO(RideBooking rideBooking){
        return new RideBookingResponseDTO(
            rideBooking.getRideId(),
            rideBooking.getRideBookingStatus(),
            rideBooking.getCreatedAt()
        );
    }

    public String getPassengerFullName() {
        return passengerFullName;
    }

    public void setPassengerFullName(String passengerFullName) {
        this.passengerFullName = passengerFullName;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public RideBookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(RideBookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
