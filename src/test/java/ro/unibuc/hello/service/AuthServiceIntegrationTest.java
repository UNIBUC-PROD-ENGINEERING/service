package ro.unibuc.hello.service;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.HelloApplication;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.request.LoginDto;
import org.springframework.http.MediaType;

@SpringBootTest(classes=HelloApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class AuthServiceIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20")
            // Exposing port 27017 from the MongoDB container.
            .withExposedPorts(27017)
            // enable sharding on MongoDB container
            .withSharding();

    // Method executed once before all tests to start the MongoDB container.
    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    // Method executed once after all tests to stop the MongoDB container.
    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // Constructing MongoDB connection URL with localhost and mapped port.
        final String MONGO_URL = "mongodb://localhost:";
        final String PORT = String.valueOf(mongoDBContainer.getMappedPort(27017));

        // Adding MongoDB connection URL property to the registry.
        registry.add("mongodb.connection.url", () -> MONGO_URL + PORT);
    }

    // Autowiring MockMvc instance for HTTP request simulation.
    @Autowired
    private MockMvc mockMvc;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void cleanUpAndAddTestData() {
        UserEntity user1 = UserEntity.builder()
                            .username("user1")
                            .email("email1")
                            .password(passwordEncoder.encode("password1"))
                            .build();

        userRepository.save(user1);
    }

    @Test
    public void testMockMvcInjection() throws Exception {
        assert webApplicationContext !=null : "WebApplicationContext is null";
        assert mockMvc != null : "MockMvc is null";
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin_ShouldLogInUser() throws Exception{
        LoginDto loginDto = LoginDto.builder().username("user1")
                            .password("password1")
                            .build();
        String requestBody = new ObjectMapper().writeValueAsString(loginDto);
        
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(jsonPath("$.role").isNotEmpty())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    
}
