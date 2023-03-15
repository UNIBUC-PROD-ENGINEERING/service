package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LocationRepository extends MongoRepository<LocationEntity, String>{
    LocationEntity findByAddress(String address);
}
