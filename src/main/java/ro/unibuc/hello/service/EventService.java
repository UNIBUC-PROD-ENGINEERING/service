package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.model.Event;
import ro.unibuc.hello.repository.EventRepository;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.repository.NotificareRepository;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    // private final NotificareRepository notificareRepository;
    
    // private final UserRepository userRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(String eventId) {
        return eventRepository.findByEventId(eventId).orElse(null);
    }

    public boolean deleteEvent(String eventId) {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }

    public List<Event> getEventsByUserId(String userId) {
        return eventRepository.findByUserId(userId);
    }

    public List<Event> getEventsByUsername(String username) {
        return eventRepository.findByUsernamesContaining(username);
    }

    public boolean inviteUser(String eventId, String username) {
        Event event = eventRepository.findByEventId(eventId).orElse(null);
        if (event != null) {
            event.addInvite(username);
            eventRepository.save(event);
            return true;
        }
        return false;
    }
}
