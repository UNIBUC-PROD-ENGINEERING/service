package ro.unibuc.hello.data;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends MongoRepository<SessionEntity, String> {

    Optional<SessionEntity> findBySessionId(String sessionId);
    void deleteByExpiresAtBefore(LocalDateTime time);
}
