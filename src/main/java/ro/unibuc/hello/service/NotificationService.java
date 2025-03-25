package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.entity.Notification;
import ro.unibuc.hello.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    
    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    
    public List<Notification> getNotificationsByClientId(String clientId) {
        return notificationRepository.findByClientId(clientId);
    }
    
    public Optional<Notification> getNotificationById(String id) {
        return notificationRepository.findById(id);
    }

    public Notification createNotification(String clientId, String message) {
        Notification notification = new Notification();
        notification.setClientId(clientId);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());

        return notificationRepository.save(notification);
    }


    public Notification markAsRead(String id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setRead(true);
            return notificationRepository.save(notification);
        } else {
            throw new IllegalArgumentException("Notification not found");
        }
    }
    
    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }
}
