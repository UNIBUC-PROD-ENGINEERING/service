package ro.unibuc.hello.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.request.LoginDto;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.service.AuthService;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class AuthControllerIntegrationTest {

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
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        userRepository.deleteAll();

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        RegisterDto registerDto = RegisterDto.builder().username("user1")
                            .email("email1@yahoo.com")
                            .password("P@ssword123")
                            .build();

        authService.register(registerDto);
    }


    @Test
    public void testRegister_ShouldRegisterUser() throws Exception{
        RegisterDto registerDto = RegisterDto.builder().username("user2")
                                .email("email2@yahoo.com")
                                .password("P@ssword123")
                                .build();

        String requestBody = new ObjectMapper().writeValueAsString(registerDto);
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user2"))
                .andExpect(jsonPath("$.email").value("email2@yahoo.com"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    public void testRegister_ShouldNotRegisterUser_WhenUserAlreadyExists() throws Exception{
        RegisterDto registerDto = RegisterDto.builder().username("user1")
                                .email("email1@yahoo.com")
                                .password("randompassword1!")
                                .build();

        String requestBody = new ObjectMapper().writeValueAsString(registerDto);
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isConflict());
    }

    @Test
    public void testLogin_ShouldLogInUser_WhenUserExists() throws Exception{
        LoginDto loginDto = LoginDto.builder().username("user1")
                            .password("P@ssword123")
                            .build();
        String requestBody = new ObjectMapper().writeValueAsString(loginDto);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.email").value("email1@yahoo.com"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    public void testLogin_ShouldNotLogInUser_WhenUserGivesWrongPassword() throws Exception{
        LoginDto loginDto = LoginDto.builder().username("user1")
                            .password("WrongP@ssword123")
                            .build();
        String requestBody = new ObjectMapper().writeValueAsString(loginDto);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    
}
