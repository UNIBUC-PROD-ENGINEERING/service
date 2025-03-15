package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.unibuc.hello.data.LocationEntity;

import java.util.List;

public interface LocationRepository extends MongoRepository<LocationEntity, String> {
    List<LocationEntity> findByName(String name);
    List<LocationEntity> findByAddress(String address);
}