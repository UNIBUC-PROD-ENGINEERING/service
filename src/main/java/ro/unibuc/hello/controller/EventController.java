package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.model.Event;
import ro.unibuc.hello.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    
    @PostMapping("/add")
    public Event addEvent(@RequestBody Event event) {
        return eventService.addEvent(event);
    }

    @GetMapping("/eventId/{eventId}")
    public Event getEventById(@PathVariable String eventId) {
        return eventService.getEventById(eventId);
    }

    @DeleteMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable String eventId) {
        return eventService.deleteEvent(eventId)
            ? "Event deleted successfully!"
            : "Event not found!";
}   


    @GetMapping("/userId/{userId}")
    public List<Event> getEventsByUserId(@PathVariable String userId) {
        return eventService.getEventsByUserId(userId);
    }

    @GetMapping("/username/{username}")
    public List<Event> getEventsByUsername(@PathVariable String username) {
        return eventService.getEventsByUsername(username);
    }

    @PostMapping("/invite/{eventId}/{username}")
    public String inviteUser(@PathVariable String eventId, @PathVariable String username) {
        return eventService.inviteUser(eventId, username)
            ? "User " + username + " invited successfully!"
            : "Event not found!";
    }
}