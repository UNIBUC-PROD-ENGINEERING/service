package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ro.unibuc.hello.service.GreetingsService;

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
public class GreetingsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GreetingsService greetingsService;

    // @Container
    // private static final GenericContainer<?> mongoContainer = new GenericContainer<>("mongo:latest")
    //         .withExposedPorts(27017);

    // final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");


    // @BeforeAll
    // static void setup() {
    //     mongoDBContainer.start();
        
    // }

    @BeforeEach
    public void cleanUpAndAddTestData() {
        greetingsService.deleteAllGreetings();

        Greeting greeting1 = new Greeting("1", "Hello 1");
        Greeting greeting2 = new Greeting("2", "Hello 2");

        greetingsService.saveGreeting(greeting1);
        greetingsService.saveGreeting(greeting2);
    }

    // @AfterAll
    // public static void afterAll() {
    //     mongoContainer.stop();
    // }

    @Test
    public void testGetAllGreetings() throws Exception {
        mockMvc.perform(get("/greetings"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].content").value("Hello 1"))
            .andExpect(jsonPath("$[1].content").value("Hello 2"));
    }

    @Test
    public void testCreateGreeting() throws Exception {
        Greeting greeting = new Greeting("3", "Hello New");

        mockMvc.perform(post("/greetings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(greeting)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.content").value("Hello New"));

        mockMvc.perform(get("/greetings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void testUpdateGreeting() throws Exception {
        Greeting greeting = new Greeting("1", "Hello Updated");

        mockMvc.perform(put("/greetings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(greeting)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.content").value("Hello Updated"));

        mockMvc.perform(get("/greetings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].content").value("Hello Updated"))
                .andExpect(jsonPath("$[1].content").value("Hello 2"));
    }

    @Test
    public void testDeleteGreeting() throws Exception {

        mockMvc.perform(delete("/greetings/1"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/greetings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].content").value("Hello 2"));
    }
}
