package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.model.Notificare;
import ro.unibuc.hello.service.NotificareService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotificareControllerTest {
    
    @Mock
    private NotificareService notificareService;

    @InjectMocks
    private NotificareController notificareController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificareController).build();
    }



    @Test
    public void testCreateNotificare() throws Exception {
        Notificare notificare = new Notificare("1", "123", "user1", "tip1", true);
        when(notificareService.createNotificare(any(Notificare.class))).thenReturn(notificare);

        mockMvc.perform(post("/api/addNotificare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"notificareId\":\"1\",\"eventId\":\"123\",\"userId\":\"user1\",\"tipVerificare\":\"tip1\",\"verificare\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificareId").value("1"))
                .andExpect(jsonPath("$.eventId").value("123"))
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.tipVerificare").value("tip1"))
                .andExpect(jsonPath("$.verificare").value(true));

        verify(notificareService, times(1)).createNotificare(any(Notificare.class));
    }

    @Test
    public void testGetNotificariByEventId() throws Exception {
        String eventId = "123";
        List<Notificare> notificari = new ArrayList<>();
        notificari.add(new Notificare("1", eventId, "user1", "tip1", false));
        notificari.add(new Notificare("2", eventId, "user2", "tip2", true));
        when(notificareService.getNotificariByEventId(eventId)).thenReturn(notificari);

        mockMvc.perform(get("/api/notificare/notificariByEventId/{eventId}", eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].eventId").value("123"))
                .andExpect(jsonPath("$[1].eventId").value("123"));

        verify(notificareService, times(1)).getNotificariByEventId(eventId);
    }


    @Test
    public void testGetNotificariByUserId() throws Exception {
        String userId = "user1";
        List<Notificare> notificari = new ArrayList<>();
        notificari.add(new Notificare("1", "123", userId, "tip1", false));
        notificari.add(new Notificare("2", "124", userId, "tip2", true));
        when(notificareService.getNotificariByUserId(userId)).thenReturn(notificari);

        mockMvc.perform(get("/api/notificare/notificariByUserId/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].eventId").value("123"))
                .andExpect(jsonPath("$[1].eventId").value("124"));

        verify(notificareService, times(1)).getNotificariByUserId(userId);
    }

    @Test
    public void testAcceptInvitation() throws Exception {
        String notificareId = "1";
        doNothing().when(notificareService).acceptInvitation(notificareId);

        mockMvc.perform(put("/api/notificare//acceptInvitation/{notificareId}", notificareId))
                .andExpect(status().isOk());

        verify(notificareService, times(1)).acceptInvitation(notificareId);
    }
}
