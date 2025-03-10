package ro.unibuc.hello.data;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String>{

    Optional<User> findByUsername(String username);

}
