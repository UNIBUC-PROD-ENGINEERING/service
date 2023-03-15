package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.ApplicationEntity;

@Repository
public interface ApplicationRepository extends MongoRepository<ApplicationEntity, String> {
}