package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import org.mockito.*;
import ro.unibuc.hello.entity.Notification;
import ro.unibuc.hello.repository.NotificationRepository;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotifications() {
        List<Notification> mockList = List.of(new Notification(), new Notification());
        when(notificationRepository.findAll()).thenReturn(mockList);

        List<Notification> result = notificationService.getAllNotifications();

        assertEquals(2, result.size());
        verify(notificationRepository).findAll();
    }

    @Test
    void testGetNotificationsByClientId() {
        String clientId = "client123";
        List<Notification> notifications = List.of(new Notification());
        when(notificationRepository.findByClientId(clientId)).thenReturn(notifications);

        List<Notification> result = notificationService.getNotificationsByClientId(clientId);

        assertEquals(1, result.size());
        verify(notificationRepository).findByClientId(clientId);
    }

    @Test
    void testGetNotificationByIdFound() {
        String notifId = "notif1";
        Notification notification = new Notification();
        when(notificationRepository.findById(notifId)).thenReturn(Optional.of(notification));

        Optional<Notification> result = notificationService.getNotificationById(notifId);

        assertTrue(result.isPresent());
        verify(notificationRepository).findById(notifId);
    }

    @Test
    void testCreateNotification() {
        String clientId = "client123";
        String message = "Test message";

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        Notification savedNotification = new Notification();
        savedNotification.setId("notif123");

        when(notificationRepository.save(any(Notification.class))).thenReturn(savedNotification);

        Notification result = notificationService.createNotification(clientId, message);

        assertEquals("notif123", result.getId());
        verify(notificationRepository).save(captor.capture());

        Notification captured = captor.getValue();
        assertEquals(clientId, captured.getClientId());
        assertEquals(message, captured.getMessage());
        assertFalse(captured.isRead());
        assertNotNull(captured.getTimestamp());
    }

    @Test
    void testMarkNotificationAsRead_Success() {
        String notifId = "notif123";
        Notification notification = new Notification();
        notification.setRead(false);

        when(notificationRepository.findById(notifId)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any())).thenReturn(notification);

        Notification result = notificationService.markAsRead(notifId);

        assertTrue(result.isRead());
        verify(notificationRepository).save(notification);
    }

    @Test
    void testMarkNotificationAsRead_NotFound() {
        String notifId = "missing";
        when(notificationRepository.findById(notifId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.markAsRead(notifId);
        });
    }

    @Test
    void testDeleteNotification() {
        String notifId = "notif123";

        notificationService.deleteNotification(notifId);

        verify(notificationRepository).deleteById(notifId);
    }
}
