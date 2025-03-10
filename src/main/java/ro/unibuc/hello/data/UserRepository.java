
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends MongoRepository<UserEntity, String>{

    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
