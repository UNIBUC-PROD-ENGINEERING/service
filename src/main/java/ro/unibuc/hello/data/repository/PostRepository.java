package ro.unibuc.hello.data.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.*;

public interface PostRepository extends MongoRepository<PostEntity, String> {
    List<PostEntity> findByUserId(String userId);
}
