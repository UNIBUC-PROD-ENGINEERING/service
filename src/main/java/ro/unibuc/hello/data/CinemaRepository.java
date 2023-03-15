package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends MongoRepository<Cinema, String> {
    Optional <Cinema> findByName (String name);
    Optional <Cinema> findByCity (String city);
}