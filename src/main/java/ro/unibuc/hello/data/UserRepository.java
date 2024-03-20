package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByLastName(String lastName);
    List<UserEntity> findByFirstName(String firstName);
    UserEntity findByUserName(String userName);
}