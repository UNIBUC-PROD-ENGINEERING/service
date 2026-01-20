package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unibuc.hello.data.TodoRepository;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.CreateTodoRequest;
import ro.unibuc.hello.dto.CreateUserRequest;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class TodoControllerIntegrationTest {

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
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanUp() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void createUser(String name, String email) throws Exception {
        CreateUserRequest request = new CreateUserRequest(name, email);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    private String createTodo(String description, String assigneeEmail) throws Exception {
        CreateTodoRequest request = new CreateTodoRequest(description, assigneeEmail);

        String response = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(description))
            .andExpect(jsonPath("$.done").value(false))
            .andExpect(jsonPath("$.assigneeEmail").value(assigneeEmail))
            .andExpect(jsonPath("$.id").exists())
            .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).get("id").asText();
    }

    @Test
    public void testCreateAndGetTodo() throws Exception {
        createUser("Alice", "alice@example.com");
        String todoId = createTodo("Buy milk", "alice@example.com");

        mockMvc.perform(get("/todos/" + todoId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Buy milk"))
            .andExpect(jsonPath("$.done").value(false))
            .andExpect(jsonPath("$.assigneeName").value("Alice"))
            .andExpect(jsonPath("$.assigneeEmail").value("alice@example.com"));
    }

    @Test
    public void testGetTodosByUser() throws Exception {
        createUser("Alice", "alice@example.com");
        createUser("Bob", "bob@example.com");
        createTodo("Buy milk", "alice@example.com");
        createTodo("Walk the dog", "alice@example.com");
        createTodo("Clean house", "bob@example.com");

        mockMvc.perform(get("/todos").param("assigneeEmail", "alice@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));

        mockMvc.perform(get("/todos").param("assigneeEmail", "bob@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testSetDone() throws Exception {
        createUser("Alice", "alice@example.com");
        String todoId = createTodo("Buy milk", "alice@example.com");

        mockMvc.perform(patch("/todos/" + todoId + "/done")
                .contentType(MediaType.APPLICATION_JSON)
                .content("true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.done").value(true));

        mockMvc.perform(patch("/todos/" + todoId + "/done")
                .contentType(MediaType.APPLICATION_JSON)
                .content("false"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    public void testAssign() throws Exception {
        createUser("Alice", "alice@example.com");
        createUser("Bob", "bob@example.com");
        String todoId = createTodo("Buy milk", "alice@example.com");

        mockMvc.perform(patch("/todos/" + todoId + "/assignee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"newAssigneeEmail\":\"bob@example.com\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.assigneeName").value("Bob"))
            .andExpect(jsonPath("$.assigneeEmail").value("bob@example.com"));
    }

    @Test
    public void testEditDescription() throws Exception {
        createUser("Alice", "alice@example.com");
        String todoId = createTodo("Buy milk", "alice@example.com");

        mockMvc.perform(patch("/todos/" + todoId + "/description")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Buy oat milk\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Buy oat milk"));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        createUser("Alice", "alice@example.com");
        String todoId = createTodo("Buy milk", "alice@example.com");

        mockMvc.perform(delete("/todos/" + todoId))
            .andExpect(status().isOk());

        mockMvc.perform(get("/todos").param("assigneeEmail", "alice@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }
}
