package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
    PostEntity findByTitle(String title);
    List<PostEntity> findByLocation(String location);
    PostEntity findByDateTime(String dateTime);
}