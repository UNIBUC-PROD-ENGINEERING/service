package ro.unibuc.hello.controller;

import ro.unibuc.hello.model.User;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.RegisterRequest;
import ro.unibuc.hello.security.JwtUtils;
import ro.unibuc.hello.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;  

public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void test_registerUser() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setAdmin(false);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password123\",\"isAdmin\":false}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully as USER!"));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void test_loginUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        User user = new User("testuser", "test@example.com", "encodedpassword", new HashSet<>(Collections.singletonList("ROLE_USER")));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedpassword")).thenReturn(true);
        when(jwtUtils.generateToken(anyString(), anyList())).thenReturn("jwt_token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.token").value("jwt_token"));
    }

    @Test
    void test_getUserByUsername_AsAdmin() throws Exception {
        String username = "testuser";
        User user = new User("testuser", "test@example.com", "password123", new HashSet<>(Collections.singletonList("ROLE_USER")));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        when(jwtUtils.getRolesFromToken("valid_token")).thenReturn(new HashSet<>(Arrays.asList("ROLE_ADMIN")));

        mockMvc.perform(get("/api/auth/users/{username}", username)
                .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username));
    }

    @Test
    void test_getUserByUsername_NotAdmin() throws Exception {
        mockMvc.perform(get("/api/auth/users/{username}", "testuser")
                .header("Authorization", "Bearer invalid_token"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Access denied! Admin role required."));
    }

    @Test
    void test_deleteUser() throws Exception {
        String username = "testuser";
        User user = new User("testuser", "test@example.com", "password123", new HashSet<>(Collections.singletonList("ROLE_USER")));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        when(jwtUtils.getRolesFromToken("valid_token")).thenReturn(new HashSet<>(Arrays.asList("ROLE_ADMIN")));

        mockMvc.perform(delete("/api/auth/users/{username}", username)
                .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully!"));

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void test_deleteUser_NotAdmin() throws Exception {
        mockMvc.perform(delete("/api/auth/users/{username}", "testuser")
                .header("Authorization", "Bearer invalid_token"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Access denied! Admin role required."));
    }

    @Test
    void test_userModelValidation() {
        Set<String> roles = new HashSet<>();
        User user = new User("testuser", "invalid_email", "password123", roles);

        assertThrows(IllegalArgumentException.class, () -> { 
            if (!user.getEmail().contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
        });
    }

    @Test
    void test_registerUser_duplicateUsername() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existinguser");
        registerRequest.setEmail("newemail@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setAdmin(false);

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"existinguser\",\"email\":\"newemail@example.com\",\"password\":\"password123\",\"isAdmin\":false}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Username already exists!"));
    }

    @Test
    void test_registerUser_duplicateEmail() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setEmail("existingemail@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setAdmin(false);

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newuser\",\"email\":\"existingemail@example.com\",\"password\":\"password123\",\"isAdmin\":false}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Email already in use!"));
    }

    @Test
    void test_loginUser_invalidPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("wrongpassword");

        User user = new User("testuser", "test@example.com", "encodedpassword", new HashSet<>(Collections.singletonList("ROLE_USER")));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "encodedpassword")).thenReturn(false);  
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"wrongpassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Invalid username or password!"));
    }

    @Test
    void test_createValidUser() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("validuser");
        registerRequest.setEmail("validemail@example.com");
        registerRequest.setPassword("validpassword123");
        registerRequest.setAdmin(false);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User("validuser", "validemail@example.com", "validpassword123", new HashSet<>(Arrays.asList("ROLE_USER"))));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"validuser\",\"email\":\"validemail@example.com\",\"password\":\"validpassword123\",\"isAdmin\":false}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully as USER!"));
    }

}
