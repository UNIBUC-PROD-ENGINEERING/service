package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.data.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findById(String id);
}
