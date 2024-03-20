package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entities.Policy;

import java.util.Optional;

@Repository
public interface PolicyRepository extends MongoRepository<Policy, String>{

    Optional<Policy> findById(String id);

}
