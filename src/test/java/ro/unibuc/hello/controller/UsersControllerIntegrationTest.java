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

import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.SessionRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.UserPostRequest;
import ro.unibuc.hello.service.UsersService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class UsersControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            .withExposedPorts(27017)
            .withSharding();

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
        final String MONGO_URL = "mongodb://localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersService usersService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        usersService.deleteAllUsers();
        sessionRepository.deleteAll(); 

        UserEntity user1 = new UserEntity("1","User One", "password1", "username1" );
        user1 = userRepository.save(user1);

        UserEntity user2 = new UserEntity("2","User Two", "password2", "username2" );
        user2 = userRepository.save(user2);

        SessionEntity session = new SessionEntity("session1", user1, LocalDateTime.now().plusMinutes(10000));
        session = sessionRepository.save(session);


    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("User One"))
                .andExpect(jsonPath("$[1].name").value("User Two"));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("User One"));
    }

    @Test
    public void testCreateUser() throws Exception {
        UserPostRequest request = new UserPostRequest("User Three", "username3", "password3");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("User Three"));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserPostRequest request = new UserPostRequest("Updated User", "username1", "password1");

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .header("X-Session-Id", "session1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .header("X-Session-Id", "session1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetUserItems() throws Exception {
        mockMvc.perform(get("/users/1/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Assuming no items initially
    }

    @Test
    public void testGetUserAuctions() throws Exception {
        mockMvc.perform(get("/users/1/auctions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Assuming no auctions initially
    }

    @Test
    public void testGetUserBids() throws Exception {
        mockMvc.perform(get("/users/1/bids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Assuming no bids initially
    }
}
