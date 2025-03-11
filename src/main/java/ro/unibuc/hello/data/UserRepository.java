package ro.unibuc.hello.data;
import ro.unibuc.hello.data.UserEntity;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByFullName(String fullName);
}
