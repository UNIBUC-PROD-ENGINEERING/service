package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.ApartmentEntity;

@Repository
public interface ApartmentRepository extends MongoRepository<ApartmentEntity, String> {
}
