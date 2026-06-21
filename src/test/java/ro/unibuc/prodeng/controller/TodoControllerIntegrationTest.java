package ro.unibuc.prodeng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.prodeng.IntegrationTestBase;
import ro.unibuc.prodeng.repository.TodoRepository;
import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.request.CreateTodoRequest;
import ro.unibuc.prodeng.request.CreateUserRequest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TodoController Integration Tests")
class TodoControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void createUser(String name, String email) throws Exception {
        CreateUserRequest request = new CreateUserRequest(name, email);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    private String createTodo(String subject, String taskName, String description, String deadline, String email) throws Exception {
        CreateTodoRequest request =
                new CreateTodoRequest(subject, taskName, description, deadline, email);

        String response = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subject").value(subject))
                .andExpect(jsonPath("$.taskName").value(taskName))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.deadline").value(deadline))
                .andExpect(jsonPath("$.done").value(false))
                .andExpect(jsonPath("$.assigneeEmail").value(email))
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).get("id").asText();
    }

    @Test
    void testCreateAndGetTodo_validTodoCreation_retrievesTodoSuccessfully() throws Exception {
        createUser("Alice", "alice@example.com");

        String todoId = createTodo(
                "Study",
                "Spring Boot",
                "Buy milk",
                "2026-06-10",
                "alice@example.com"
        );

        mockMvc.perform(get("/api/todos/" + todoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Study"))
                .andExpect(jsonPath("$.taskName").value("Spring Boot"))
                .andExpect(jsonPath("$.description").value("Buy milk"))
                .andExpect(jsonPath("$.deadline").value("2026-06-10"))
                .andExpect(jsonPath("$.done").value(false))
                .andExpect(jsonPath("$.assigneeName").value("Alice"))
                .andExpect(jsonPath("$.assigneeEmail").value("alice@example.com"));

        assertTrue(todoRepository.findById(todoId).isPresent());
        assertEquals("Buy milk", todoRepository.findById(todoId).orElseThrow().description());
    }

    @Test
    void testGetTodosByUser_multipleUsersWithDifferentTodos_filtersCorrectly() throws Exception {
        createUser("Alice", "alice@example.com");
        createUser("Bob", "bob@example.com");

        createTodo("S1", "T1", "Buy milk", "2026-06-10", "alice@example.com");
        createTodo("S2", "T2", "Walk dog", "2026-06-10", "alice@example.com");
        createTodo("S3", "T3", "Clean house", "2026-06-10", "bob@example.com");

        mockMvc.perform(get("/api/todos").param("assigneeEmail", "alice@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        mockMvc.perform(get("/api/todos").param("assigneeEmail", "bob@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        assertEquals(3, todoRepository.findAll().size());
    }

    @Test
    void testSetDone_toggleDoneStatus_updatesStatusCorrectly() throws Exception {
        createUser("Alice", "alice@example.com");
        String todoId = createTodo("S", "T", "Buy milk", "2026-06-10", "alice@example.com");

        mockMvc.perform(patch("/api/todos/" + todoId + "/done")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.done").value(true));

        assertTrue(todoRepository.findById(todoId).orElseThrow().done());
    }

    @Test
    void testAssign_reassignToDifferentUser_updateAssigneeSuccessfully() throws Exception {
        createUser("Alice", "alice@example.com");
        createUser("Bob", "bob@example.com");

        String todoId = createTodo("S", "T", "Buy milk", "2026-06-10", "alice@example.com");

        mockMvc.perform(patch("/api/todos/" + todoId + "/assignee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newAssigneeEmail\":\"bob@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assigneeName").value("Bob"))
                .andExpect(jsonPath("$.assigneeEmail").value("bob@example.com"));

        String bobId = userRepository.findByEmail("bob@example.com").orElseThrow().id();
        assertEquals(bobId, todoRepository.findById(todoId).orElseThrow().assignedUserId());
    }

    @Test
    void testEditDescription_validNewDescription_updatesDescriptionSuccessfully() throws Exception {
        createUser("Alice", "alice@example.com");

        String todoId = createTodo(
                "Programare",
                "Tema laborator",
                "Rezolva exercitiile pentru laborator",
                "2026-06-10",
                "alice@example.com"
        );

        mockMvc.perform(patch("/api/todos/" + todoId + "/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "subject": "Programare",
                                  "taskName": "Tema laborator actualizata",
                                  "description": "Rezolva exercitiile si incarca proiectul pe GitHub",
                                  "deadline": "2026-06-11"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Programare"))
                .andExpect(jsonPath("$.taskName").value("Tema laborator actualizata"))
                .andExpect(jsonPath("$.description").value("Rezolva exercitiile si incarca proiectul pe GitHub"))
                .andExpect(jsonPath("$.deadline").value("2026-06-11"));

        var savedTodo = todoRepository.findById(todoId).orElseThrow();

        assertEquals("Programare", savedTodo.subject());
        assertEquals("Tema laborator actualizata", savedTodo.taskName());
        assertEquals("Rezolva exercitiile si incarca proiectul pe GitHub", savedTodo.description());
        assertEquals("2026-06-11", savedTodo.deadline());
    }

    @Test
    void testDeleteTodo_existingTodo_deletesSuccessfully() throws Exception {
        createUser("Alice", "alice@example.com");

        String todoId = createTodo("S", "T", "Buy milk", "2026-06-10", "alice@example.com");

        mockMvc.perform(delete("/api/todos/" + todoId))
                .andExpect(status().isNoContent());

        assertFalse(todoRepository.existsById(todoId));
    }
}