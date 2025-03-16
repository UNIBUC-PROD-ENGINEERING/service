package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.unibuc.hello.model.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    boolean existsByLicensePlate(String licensePlate);
}
