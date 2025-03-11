package ro.unibuc.hello.data.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.*;

import java.util.List;

public interface FriendshipRepository extends MongoRepository<FriendshipEntity, String> {

    List<FriendshipEntity> findByUserId1OrUserId2(String userId1, String userId2);

    FriendshipEntity findByUserId1AndUserId2(String userId1, String userId2);

    List<FriendshipEntity> findByStatusAndUserId1OrUserId2(String status, String userId1, String userId2);
    
}
