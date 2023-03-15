package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.Medicament;

import java.util.List;

@Repository
public interface MedicamentRepository extends MongoRepository<MedicamentEntity, String> {

    MedicamentEntity findByName(String name);

}
