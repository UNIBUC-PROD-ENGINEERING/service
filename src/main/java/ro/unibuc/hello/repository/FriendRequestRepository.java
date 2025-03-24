package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import ro.unibuc.hello.data.FriendRequestEntity;

@Repository
public interface FriendRequestRepository extends MongoRepository<FriendRequestEntity, String> {
    List<FriendRequestEntity> findByToUserIdAndStatus(String toUserId, String status);
    List<FriendRequestEntity> findByFromUserIdAndStatus(String fromUserId, String status);
    List<FriendRequestEntity> findByToUserId(String toUserId);
    List<FriendRequestEntity> findByFromUserId(String fromUserId);
    void deleteByFromUserIdAndToUserIdAndStatus(String fromUserId, String toUserId, String status);
    List<FriendRequestEntity> findByStatusAndFromUserIdAndToUserId(String status, String fromUserId, String toUserId);
    List<FriendRequestEntity> findByStatusAndToUserIdAndFromUserId(String status, String toUserId, String fromUserId);
    List<FriendRequestEntity> findByFromUserIdAndToUserId(String fromUserId, String toUserId);
}

