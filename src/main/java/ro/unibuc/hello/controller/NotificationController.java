package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.entity.Notification;
import ro.unibuc.hello.service.NotificationService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/client/{clientId}")
    public List<Notification> getNotificationsByClientId(@PathVariable String clientId) {
        return notificationService.getNotificationsByClientId(clientId);
    }

    @GetMapping("/{id}")
    public Optional<Notification> getNotificationById(@PathVariable String id) {
        return notificationService.getNotificationById(id);
    }

    @PostMapping("/send")
    public Notification createNotification(@RequestParam String clientId, @RequestParam String message) {
        return notificationService.createNotification(clientId, message);
    }

    @PutMapping("/{id}/read")
    public Notification markNotificationAsRead(@PathVariable String id) {
        return notificationService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
    }
}