package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.service.NotificareService;
import ro.unibuc.hello.model.Notificare;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class NotificareControllerIntegrationTest {
    
    @SuppressWarnings("resource")
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withEnv("MONGO_INITDB_ROOT_USERNAME","root") // user
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "example") // password
            .withEnv("MONGO_INITDB_DATABASE", "testdb") // dbname
            .withCommand("--auth");

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        final String MONGO_URL = "mongodb://root:example@localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificareService notificareService;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        notificareService.deleteAllNotificari();
        
        Notificare notificare1 = new Notificare("1", "user1", "event1", "creator", true);
        Notificare notificare2 = new Notificare("2", "user2", "event2", "invitat", false);

        notificareService.createNotificare(notificare1);
        notificareService.createNotificare(notificare2);
    }

    @Test
    public void testAcceptInvitation(String notificareId) throws Exception {
        mockMvc.perform(post("/api/notificare/acceptInvitation/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notificareId").value("2"))
                .andExpect(jsonPath("$.userId").value("user2"))
                .andExpect(jsonPath("$.eventId").value("event2"))
                .andExpect(jsonPath("$.tipVerificare").value("invitat"))
                .andExpect(jsonPath("$.verificare").value(true));
    }

}
