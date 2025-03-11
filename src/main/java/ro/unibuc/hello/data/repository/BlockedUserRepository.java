package ro.unibuc.hello.data.repository;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.*;

public interface BlockedUserRepository extends MongoRepository<BlockedUserEntity, String> {

    List<BlockedUserEntity> findByBlockerId(String blockerId);

    BlockedUserEntity findByBlockerIdAndBlockedUserId(String blockerId, String blockedUserId);

    List<BlockedUserEntity> findByBlockedUserId(String blockedUserId);
    
}
