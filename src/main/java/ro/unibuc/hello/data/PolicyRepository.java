package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyRepository extends MongoRepository<PolicyEntity, String>{

    Optional<PolicyEntity> findById(String id);

}
