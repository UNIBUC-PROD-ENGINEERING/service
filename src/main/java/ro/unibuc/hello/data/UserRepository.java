package ro.unibuc.hello.data;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,String>{

    Optional<UserEntity> findByUsername(String username);

    @Query(value = "{ $text: { $search: ?0 } }", sort = "{ score: { $meta: 'textScore' } }")
    List<UserEntity> searchUsersByKeyword(String keyword);

}
