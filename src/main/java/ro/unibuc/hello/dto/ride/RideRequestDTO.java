package ro.unibuc.hello.dto.ride;

import ro.unibuc.hello.model.Ride;
import java.time.Instant;


public class RideRequestDTO {
    private String driverId;
    private String vechicleId;
    private String startLocation;
    private String endLocation;
    private Instant departureTime;
    private Integer seatPrice;
    private Integer seatsAvailable;
    private String carLicensePlate;

    public Ride toEntity() {
        return new Ride(
            this.driverId,
            this.vechicleId,
            this.startLocation,
            this.endLocation,
            this.departureTime,
            this.seatPrice,
            this.seatsAvailable,
            this.carLicensePlate
        );
    }
}
