package ro.unibuc.prodeng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.request.ChangeNameRequest;
import ro.unibuc.prodeng.request.CreateUserRequest;
import ro.unibuc.prodeng.response.UserResponse;
import ro.unibuc.prodeng.service.MetricsService;
import ro.unibuc.prodeng.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private MetricsService metricsService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private UserResponse testUser1 = new UserResponse("1", "John Doe", "john@example.com");
    private UserResponse testUser2 = new UserResponse("2", "Jane Smith", "jane@example.com");
    private CreateUserRequest createUserRequest = new CreateUserRequest("John Doe", "john@example.com");
    private ChangeNameRequest changeNameRequest = new ChangeNameRequest("John Updated");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers_withMultipleUsers_returnsListOfUsers() throws Exception {
        List<UserResponse> users = Arrays.asList(testUser1, testUser2);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].email", is("john@example.com")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].name", is("Jane Smith")))
                .andExpect(jsonPath("$[1].email", is("jane@example.com")));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetAllUsers_withNoUsers_returnsEmptyList() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_existingUserRequested_returnsUser() throws Exception {
        String userId = "1";
        when(userService.getUserById(userId)).thenReturn(testUser1);

        mockMvc.perform(get("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john@example.com")));

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserById_nonExistingUserRequested_returnsNotFound() throws Exception {
        String userId = "999";
        when(userService.getUserById(userId)).thenThrow(new EntityNotFoundException("User"));

        mockMvc.perform(get("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testCreateUser_validRequestProvided_createsAndReturnsUser() throws Exception {
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(testUser1);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john@example.com")));

        verify(userService, times(1)).createUser(any(CreateUserRequest.class));
        verify(metricsService, times(1)).recordUserCreated();
    }

    @Test
    void testUpdateUser_existingUserRequested_updatesAndReturnsUser() throws Exception {
        String userId = "1";
        UserResponse updatedUser = new UserResponse("1", "John Updated", "john@example.com");
        when(userService.changeName(eq(userId), eq("John Updated"))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeNameRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("John Updated")))
                .andExpect(jsonPath("$.email", is("john@example.com")));

        verify(userService, times(1)).changeName(userId, "John Updated");
    }

    @Test
    void testUpdateUser_nonExistingUserRequested_returnsNotFound() throws Exception {
        String userId = "999";
        when(userService.changeName(eq(userId), anyString()))
                .thenThrow(new EntityNotFoundException("User"));

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeNameRequest)))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).changeName(eq(userId), anyString());
    }
}