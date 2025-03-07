package ro.unibuc.hello.repository;

import ro.unibuc.hello.data.ReviewEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<ReviewEntity, String> {
}
