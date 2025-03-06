package ro.unibuc.hello.repository;

import ro.unibuc.hello.data.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
}
