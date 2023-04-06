package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findById(long Id);
    List<UserEntity> findByFirstNameContaining(String firstName);
    List<UserEntity> findByLastNameContaining(String lastName);
    List<UserEntity> findByEmailContaining(String email);
    List<UserEntity> findAll();
    List<UserEntity> findByNameContaining(String name);
    void deleteById(long id);
}
