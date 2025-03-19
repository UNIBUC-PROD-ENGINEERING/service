package ro.unibuc.hello.repository;

import ro.unibuc.hello.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByClientId(String clientId);
}