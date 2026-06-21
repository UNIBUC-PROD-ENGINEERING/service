package ro.unibuc.prodeng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.unibuc.prodeng.IntegrationTestBase;
import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.request.ChangeNameRequest;
import ro.unibuc.prodeng.request.CreateUserRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("UserController Integration Tests")
class UserControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    private String createUser(String name, String email) throws Exception {
        CreateUserRequest request = new CreateUserRequest(name, email);

        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asText();
    }

    @Test
    void testCreateUser_validUser_savesUserInDatabase() throws Exception {
        String userId = createUser("Alice", "alice@example.com");

        assertTrue(userRepository.existsById(userId));
        assertEquals("Alice", userRepository.findById(userId).orElseThrow().name());
        assertEquals("alice@example.com", userRepository.findById(userId).orElseThrow().email());
    }

    @Test
    void testGetAllUsers_multipleUsers_returnsAllUsers() throws Exception {
        createUser("Alice", "alice@example.com");
        createUser("Bob", "bob@example.com");

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    void testGetUserById_existingUser_returnsUser() throws Exception {
        String userId = createUser("Alice", "alice@example.com");

        mockMvc.perform(get("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void testGetUserByEmail_existingUser_returnsUser() throws Exception {
        createUser("Alice", "alice@example.com");

        mockMvc.perform(get("/api/users/by-email")
                        .param("email", "alice@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));

        assertTrue(userRepository.findByEmail("alice@example.com").isPresent());
    }

    @Test
    void testUpdateUserWithPut_existingUser_updatesName() throws Exception {
        String userId = createUser("Alice", "alice@example.com");

        ChangeNameRequest request = new ChangeNameRequest("Alicia");

        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Alicia"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));

        assertEquals("Alicia", userRepository.findById(userId).orElseThrow().name());
    }

    @Test
    void testChangeNameWithPatch_existingUser_updatesName() throws Exception {
        String userId = createUser("Alice", "alice@example.com");

        ChangeNameRequest request = new ChangeNameRequest("Alice Updated");

        mockMvc.perform(patch("/api/users/" + userId + "/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Alice Updated"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));

        assertEquals("Alice Updated", userRepository.findById(userId).orElseThrow().name());
    }

    @Test
    void testDeleteUser_existingUser_removesUserFromDatabase() throws Exception {
        String userId = createUser("Alice", "alice@example.com");

        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.existsById(userId));
    }

    @Test
    void testCreateUser_duplicateEmail_returnsBadRequest() throws Exception {
        createUser("Alice", "alice@example.com");

        CreateUserRequest duplicateRequest = new CreateUserRequest("Alice Clone", "alice@example.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateRequest)))
                .andExpect(status().isBadRequest());

        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void testGetUserById_missingUser_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/users/missing-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}