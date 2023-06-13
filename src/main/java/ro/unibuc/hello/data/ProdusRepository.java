package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.ProdusDTO;


@Repository
public interface ProdusRepository extends MongoRepository<ProdusDTO, String> {


}
