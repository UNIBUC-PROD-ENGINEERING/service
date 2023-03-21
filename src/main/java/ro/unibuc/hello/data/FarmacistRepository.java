package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.Farmacist;

import java.util.List;

@Repository
public interface FarmacistRepository extends MongoRepository<FarmacistEntity, String> {

    FarmacistEntity findByName(String name);

}
