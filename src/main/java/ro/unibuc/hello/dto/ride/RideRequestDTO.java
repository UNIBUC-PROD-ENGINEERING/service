package ro.unibuc.hello.dto.ride;

import ro.unibuc.hello.model.Ride;
import java.time.Instant;

import lombok.Getter;

public class RideRequestDTO {
    private String driverId;
    private String startLocation;
    private String endLocation;
    private Instant departureTime;
    private Instant arrivalTime;
    private Integer seatPrice;
    private Integer seatsAvailable;
    private String carLicensePlate;

    public Ride toEntity() {
        return new Ride(
            this.driverId,
            this.startLocation,
            this.endLocation,
            this.departureTime,
            this.arrivalTime,
            this.seatPrice,
            this.seatsAvailable,
            this.carLicensePlate
        );
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(Integer seatPrice) {
        this.seatPrice = seatPrice;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
    }
}
