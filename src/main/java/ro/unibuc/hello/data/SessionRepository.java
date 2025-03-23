package ro.unibuc.hello.data;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends MongoRepository<SessionEntity, String> {

    Optional<SessionEntity> findBySessionId(String sessionId);
}
