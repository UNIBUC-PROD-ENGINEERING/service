package ro.unibuc.hello.data.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.*;

public interface CommentRepository extends MongoRepository<CommentEntity, String> {

    List<CommentEntity> findByPostId(String postId);

    List<CommentEntity> findByUserId(String userId);
    
}
