package ro.unibuc.hello.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ro.unibuc.hello.enums.RideStatus;

@Document("rides")
public class Ride {
    @Id
    private String id;
    private String driverId;
    private String vehicleId;
    private String startLocation;
    private String endLocation;
    private Instant departureTime;
    private Integer seatPrice;
    private Integer seatsAvailable;
    private String carLicensePlate;
    private RideStatus status;

    public Ride() {}

    
    public Ride(String driverId, String vehicleId, String startLocation, String endLocation, 
                Instant departureTime, Integer seatPrice, Integer seatsAvailable, 
                String carLicensePlate) {
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.seatPrice = seatPrice;
        this.seatsAvailable = seatsAvailable;
        this.carLicensePlate = carLicensePlate;
        this.status = RideStatus.SCHEDULED;
    }

    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }

    public Instant getDepartureTime() { return departureTime; }
    public void setDepartureTime(Instant departureTime) { this.departureTime = departureTime; }

    public Integer getSeatPrice() { return seatPrice; }
    public void setSeatPrice(Integer seatPrice) { this.seatPrice = seatPrice; }

    public Integer getSeatsAvailable() { return seatsAvailable; }
    public void setSeatsAvailable(Integer seatsAvailable) { this.seatsAvailable = seatsAvailable; }

    public String getCarLicensePlate() { return carLicensePlate; }
    public void setCarLicensePlate(String carLicensePlate) { this.carLicensePlate = carLicensePlate; }

    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", driverId='" + driverId + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", departureTime=" + departureTime +
                ", seatPrice=" + seatPrice +
                ", seatsAvailable=" + seatsAvailable +
                ", carLicensePlate='" + carLicensePlate + '\'' +
                ", status=" + status +
                '}';
    }

}
