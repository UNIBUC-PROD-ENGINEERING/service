package ro.unibuc.hello.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.unibuc.hello.model.Vehicle;


public class VehicleDTO {
    private String userId;
    private String brand;
    private String model;
    private String licensePlate;

    public VehicleDTO(String userId, String brand, String model, String licensePlate) {
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
    }

    public Vehicle toEntity() {
        return new Vehicle(
            this.userId,
            this.brand,
            this.model,
            this.licensePlate
        );
    }

    public static VehicleDTO toDTO(Vehicle vehicle) {
        return new VehicleDTO(
            vehicle.getUserId(),
            vehicle.getBrand(),
            vehicle.getModel(),
            vehicle.getLicensePlate()
        );
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
