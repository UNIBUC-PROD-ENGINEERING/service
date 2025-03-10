package ro.unibuc.hello.data;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,String>{

    Optional<UserEntity> findByUsername(String username);

}
