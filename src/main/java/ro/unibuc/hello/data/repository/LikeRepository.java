package ro.unibuc.hello.data.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.*;

public interface LikeRepository  extends MongoRepository<LikeEntity, String> {

    List<LikeEntity> findByUserId(String userId);

    List<LikeEntity> findByPostId(String postId);

    List<LikeEntity> findByCommentId(String commentId);

    LikeEntity findByUserIdAndPostId(String userId, String postId);

    LikeEntity findByUserIdAndCommentId(String userId, String commentId);
}
