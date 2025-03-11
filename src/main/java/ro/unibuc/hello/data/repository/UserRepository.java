package ro.unibuc.hello.data.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.*;

public interface UserRepository extends MongoRepository<UserEntity, String>{

    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
