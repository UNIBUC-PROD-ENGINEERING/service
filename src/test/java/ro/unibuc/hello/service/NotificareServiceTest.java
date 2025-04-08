package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.model.Notificare;
import ro.unibuc.hello.model.Event;
import ro.unibuc.hello.model.User;
import ro.unibuc.hello.repository.NotificareRepository;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.service.EventService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class NotificareServiceTest {

    @Mock
    private NotificareRepository notificareRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventService eventService;
    @InjectMocks
    private NotificareService notificareService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNotificariByEventId() {
        String eventId = "123";
        List<Notificare> notificari = new ArrayList<>();
        notificari.add(new Notificare("1", eventId, "user1", "tip1", false));
        when(notificareRepository.findByEventId(eventId)).thenReturn(notificari);
        
        List<Notificare> result = notificareService.getNotificariByEventId(eventId);

        assertEquals(1, result.size());
        assertEquals(eventId, result.get(0).getEventId());
        verify(notificareRepository, times(1)).findByEventId(eventId);
    }

    @Test
    public void testCreateNotificare() {
        Notificare notificare = new Notificare("1", "123", "user1", "tip1", true);
        when(notificareRepository.save(notificare)).thenReturn(notificare);

        Notificare result = notificareService.createNotificare(notificare);

        assertEquals(notificare, result);
        verify(notificareRepository, times(1)).save(notificare);
    }


    @Test
    public void testCreateNotificaribyUserId() {
        String userId = "user1";
        List<Notificare> existingNotificari = new ArrayList<>();
        when(notificareRepository.findByUserId(userId)).thenReturn(existingNotificari);

        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.setEventId("123");
        event.setUserId(userId);
        event.setUsernames(List.of("username2", "username3"));
        events.add(event);
        when(eventService.getEventsByUserId(userId)).thenReturn(events);

        User user2 = new User();
        user2.setId("user2");
        user2.setUsername("username2");

        User user3 = new User();
        user3.setId("user3");
        user3.setUsername("username3");

        when(userRepository.findByUsername("username2")).thenReturn(Optional.of(user2));
        when(userRepository.findByUsername("username3")).thenReturn(Optional.of(user3));

        notificareService.createNotificaribyUserId(userId);

        verify(notificareRepository, times(1)).findByUserId(userId);
        verify(eventService, times(1)).getEventsByUserId(userId);

        ArgumentCaptor<Notificare> notificareCaptor = ArgumentCaptor.forClass(Notificare.class);
        verify(notificareRepository, times(3)).save(notificareCaptor.capture());
        List<Notificare> capturedNotificari = notificareCaptor.getAllValues();

        Notificare notificare1 = capturedNotificari.get(0);
        assertEquals("123", notificare1.getEventId());
        assertEquals("user1", notificare1.getUserId());
        assertEquals("creator", notificare1.getTipVerificare());
        assertEquals(true, notificare1.getVerificare());

        Notificare notificare2 = capturedNotificari.get(1);
        assertEquals("123", notificare2.getEventId());
        assertEquals("user2", notificare2.getUserId());
        assertEquals("invitat", notificare2.getTipVerificare());
        assertEquals(false, notificare2.getVerificare());

        Notificare notificare3 = capturedNotificari.get(2);
        assertEquals("123", notificare3.getEventId());
        assertEquals("user3", notificare3.getUserId());
        assertEquals("invitat", notificare3.getTipVerificare());
        assertEquals(false, notificare3.getVerificare());

    }

        
    @Test
    public void testGetNotificariByUserId() {
        String userId = "user1";
        List<Notificare> notificari = new ArrayList<>();
        notificari.add(new Notificare("1", "123", userId, "tip1", false));
        notificari.add(new Notificare("2", "124", userId, "tip2", true));
        when(notificareRepository.findByUserId(userId)).thenReturn(notificari);
    
        List<Notificare> result = notificareService.getNotificariByUserId(userId);
    
        assertEquals(2, result.size());
        assertEquals("123", result.get(0).getEventId());
        assertEquals("124", result.get(1).getEventId());
        verify(notificareRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testAcceptInvitation() {
        String notificareId = "1";
        Notificare notificare = new Notificare(notificareId, "123", "user1", "invitat", false);
        when(notificareRepository.findByNotificareId(notificareId)).thenReturn(notificare);

        Notificare savedNotificare = notificareService.acceptInvitation(notificareId);
        
        verify(notificareRepository, times(1)).findByNotificareId(notificareId);
        verify(notificareRepository, times(1)).save(savedNotificare);

        assertNotNull(savedNotificare);
        assertEquals(notificareId, savedNotificare.getNotificareId());
        assertEquals(true, savedNotificare.getVerificare());
    }


}
