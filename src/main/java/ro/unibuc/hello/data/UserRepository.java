package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    public UserEntity findByEmail(String email);
}
