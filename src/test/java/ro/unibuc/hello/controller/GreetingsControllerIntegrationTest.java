package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.config.MongoDBTestContainerConfig;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import ro.unibuc.hello.service.GreetingsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
@Tag("IntegrationTest")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GreetingsControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private GreetingsService greetingsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private InformationRepository informationRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Greeting greeting1 = new Greeting("1", "Hello 1");
        Greeting greeting2 = new Greeting("2", "Hello 2");

        greetingsService.saveGreeting(greeting1);
        greetingsService.saveGreeting(greeting2);
    }

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
