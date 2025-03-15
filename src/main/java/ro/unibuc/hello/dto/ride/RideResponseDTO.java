package ro.unibuc.hello.dto.ride;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;


import ro.unibuc.hello.enums.RideStatus;
import ro.unibuc.hello.model.Ride;

public class RideResponseDTO {
    private String driverFullName;
    private String startLocation;
    private String endLocation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant departureTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant arrivalTime;
    private int seatPrice;
    private int seatsAvailable;
    private String carLicensePlate;
    private Double driverRating;
    private RideStatus status;

    public RideResponseDTO() {}

    public RideResponseDTO(String startLocation, String endLocation, Instant departureTime,
                            Instant arrivalTime, int seatPrice, int seatsAvailable, 
                            String carLicensePlate, RideStatus status) {
        
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatPrice = seatPrice;
        this.seatsAvailable = seatsAvailable;
        this.carLicensePlate = carLicensePlate;
        this.status = status;
    }

    public void setDriverrating(Double rating) {
        this.driverRating = rating;
    }

    public static RideResponseDTO toDTO(Ride ride) {
        return new RideResponseDTO(
            ride.getStartLocation(),
            ride.getEndLocation(),
            ride.getDepartureTime(),
            ride.getArrivalTime(),
            ride.getSeatPrice(),
            ride.getSeatsAvailable(),
            ride.getCarLicensePlate(),
            ride.getStatus()
        );
    }
}
