package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.data.UserEntity;
public interface UserRepository extends  MongoRepository<UserEntity, String>{
}
