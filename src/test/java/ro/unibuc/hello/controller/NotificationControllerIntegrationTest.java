package ro.unibuc.hello.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.context.annotation.Import;
import ro.unibuc.hello.config.NoSecurityConfig;
import ro.unibuc.hello.entity.Client;
import ro.unibuc.hello.entity.Notification;
import ro.unibuc.hello.repository.ClientRepository;
import ro.unibuc.hello.repository.NotificationRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
@Import(NoSecurityConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("IntegrationTest")
public class NotificationControllerIntegrationTest {

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withCommand("--replSet", "rs0");

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired private MockMvc mockMvc;
    @Autowired private NotificationRepository notificationRepository;
    @Autowired private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll();
        clientRepository.deleteAll();

        client = new Client();
        client.setEmail("client@example.com");
        client = clientRepository.save(client);
    }

    @Test
    void testCreateNotification_success() throws Exception {
        mockMvc.perform(post("/api/notifications/send")
                        .param("clientId", client.getId())
                        .param("message", "Salut test!")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(client.getId()))
                .andExpect(jsonPath("$.message").value("Salut test!"));
    }

    @Test
    void testGetAllNotifications_success() throws Exception {
        Notification notif = new Notification();
        notif.setClientId(client.getId());
        notif.setMessage("Test message");
        notificationRepository.save(notif);

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientId").value(client.getId()));
    }

    @Test
    void testGetNotificationByClientId_success() throws Exception {
        Notification notif = new Notification();
        notif.setClientId(client.getId());
        notif.setMessage("Client message");
        notificationRepository.save(notif);

        mockMvc.perform(get("/api/notifications/client/" + client.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Client message"));
    }

    @Test
    void testMarkNotificationAsRead_success() throws Exception {
        Notification notif = new Notification();
        notif.setClientId(client.getId());
        notif.setMessage("Mark me");
        notif.setRead(false);
        notif = notificationRepository.save(notif);

        mockMvc.perform(put("/api/notifications/" + notif.getId() + "/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.read").value(true));
    }

    @Test
    void testDeleteNotification_success() throws Exception {
        Notification notif = new Notification();
        notif.setClientId(client.getId());
        notif.setMessage("Delete me");
        notif = notificationRepository.save(notif);

        mockMvc.perform(delete("/api/notifications/" + notif.getId()))
                .andExpect(status().isOk());
    }
}
