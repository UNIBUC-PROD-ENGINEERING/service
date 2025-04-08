// --- EventControllerTest.java ---
package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.model.Event;
import ro.unibuc.hello.service.EventService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testAddEvent() throws Exception {
        Event event = new Event();
        event.setName("New Event");
        when(eventService.addEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/api/event/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Event"));

        verify(eventService, times(1)).addEvent(any(Event.class));
    }

    @Test
    public void testGetEventById() throws Exception {
        Event event = new Event();
        event.setEventId("123");
        event.setName("Sample Event");
        when(eventService.getEventById("123")).thenReturn(event);

        mockMvc.perform(get("/api/event/eventId/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value("123"))
                .andExpect(jsonPath("$.name").value("Sample Event"));

        verify(eventService, times(1)).getEventById("123");
    }

    // @Test
    // public void testDeleteEvent() throws Exception {
    //     when(eventService.deleteEvent("123")).thenReturn(true);

    //     mockMvc.perform(delete("/api/event/delete/123"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("true"));

    //     verify(eventService, times(1)).deleteEvent("123");
    // }

    @Test
    public void testGetEventsByUserId() throws Exception {
        List<Event> events = Arrays.asList(new Event(), new Event());
        when(eventService.getEventsByUserId("user1")).thenReturn(events);

        mockMvc.perform(get("/api/event/userId/user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(eventService, times(1)).getEventsByUserId("user1");
    }

    // @Test
    // public void testInviteUser() throws Exception {
    //     when(eventService.inviteUser("e123", "username1")).thenReturn(true);

    //     mockMvc.perform(put("/api/event/invite/e123/username1"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("true"));

    //     verify(eventService, times(1)).inviteUser("e123", "username1");
    // }
}
