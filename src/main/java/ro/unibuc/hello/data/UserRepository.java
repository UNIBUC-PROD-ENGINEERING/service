package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.Product;
import ro.unibuc.hello.dto.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'userId' : ?0 }")
    User findUserById(String userId);
}
