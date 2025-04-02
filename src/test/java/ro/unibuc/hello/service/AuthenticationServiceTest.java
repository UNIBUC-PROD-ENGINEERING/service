package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.LoginResponse;
import ro.unibuc.hello.dto.RegisterRequest;
import ro.unibuc.hello.exception.UserNotFoundException;
import ro.unibuc.hello.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        loginRequest = new LoginRequest("testUser", "password123");
        registerRequest = new RegisterRequest("newUser", "password123");
        mockUser = new User("testUser", "encodedPassword");
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(java.util.Optional.of(mockUser));
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(loginRequest.getUsername())).thenReturn("mockToken");

        // Act
        LoginResponse loginResponse = authenticationService.login(loginRequest);

        // Assert
        assertNotNull(loginResponse);
        assertEquals("mockToken", loginResponse.getToken());

        // Verify interactions
        verify(userRepository).findByUsername(loginRequest.getUsername());
        verify(authenticationManager).authenticate(any());
        verify(jwtUtil).generateToken(loginRequest.getUsername());
    }

    @Test
    void testLoginUserNotFound() {
        // Arrange
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(java.util.Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> authenticationService.login(loginRequest));
        assertEquals("User not found", exception.getMessage());

        // Verify interactions
        verify(userRepository).findByUsername(loginRequest.getUsername());
    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        when(userRepository.findByUsername(registerRequest.getUsername())).thenReturn(java.util.Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtUtil.generateToken(registerRequest.getUsername())).thenReturn("mockToken");

        // Act
        LoginResponse loginResponse = authenticationService.register(registerRequest);

        // Assert
        assertNotNull(loginResponse);
        assertEquals("mockToken", loginResponse.getToken());

        // Verify interactions
        verify(userRepository).findByUsername(registerRequest.getUsername());
        verify(passwordEncoder).encode(registerRequest.getPassword());
        verify(userRepository).save(any(User.class));
        verify(jwtUtil).generateToken(registerRequest.getUsername());
    }

    @Test
    void testRegisterUserAlreadyExists() {
        // Arrange
        when(userRepository.findByUsername(registerRequest.getUsername())).thenReturn(java.util.Optional.of(mockUser));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authenticationService.register(registerRequest));
        assertEquals("User already exists", exception.getMessage());

        // Verify interactions
        verify(userRepository).findByUsername(registerRequest.getUsername());
    }
}
