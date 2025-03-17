package ro.unibuc.hello.model;

import java.lang.annotation.Inherited;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.unibuc.hello.enums.RideBookingStatus;


@Document("ride_passengers")
@Getter
@Setter
@NoArgsConstructor
public class RideBooking {
    @Id
    private String id;
    private String rideId;
    private String passengerId;
    private RideBookingStatus bookingStatus;
    private Instant createdAt;


    public RideBooking(String rideId, String passengerId, Instant createdAt)
    {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.createdAt = createdAt;
        this.bookingStatus = RideBookingStatus.BOOKED;
    }

    @Override
    public String toString(){
        return "RideBooking{" +
                "id='" + id + '\'' +
                ", rideId='" + rideId + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", bookingStatus=" + bookingStatus +
                '}';
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public Instant getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt){
        this.createdAt = createdAt;
    }
     
    public RideBookingStatus getRideBookingStatus() {
        return bookingStatus;
    }

    public void setRideBookingStatus(RideBookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

}
