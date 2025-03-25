package ro.unibuc.hello.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ro.unibuc.hello.auth.AuthInterceptor;
import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.Session;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.GlobalExceptionHandler;
import ro.unibuc.hello.exception.InvalidSessionException;
import ro.unibuc.hello.exception.LoginFailedException;
import ro.unibuc.hello.service.SessionsService;

public class SessionsControllerTest {
    @Mock
    private SessionsService sessionsService;

    @InjectMocks
    private SessionsController sessionsController;

    @InjectMocks
    private AuthInterceptor authInterceptor;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sessionsController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .addInterceptors(authInterceptor)
            .build();


        UserEntity user = new UserEntity("11", "user 1", "password1", "username1");
        SessionEntity session = new SessionEntity("session1", user, null);
        doThrow(new InvalidSessionException("Invalid session")).when(sessionsService).getValidSession(anyString());
        doReturn(session).when(sessionsService).getValidSession("session1");
        doThrow(new InvalidSessionException("Expired session")).when(sessionsService).getValidSession("session2");
    }

    @Test
    void testLogin_Success() throws Exception {
        // Arrange
        Session session = new Session("session1", new User("11", "user 1"));
        when(sessionsService.login(any(LoginRequest.class))).thenReturn(session);

        // Act & Assert
        mockMvc.perform(post("/session/login")
            .content("{\"username\":\"username\",\"password\":\"password\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sessionId").value("session1"))
            .andExpect(jsonPath("$.user.id").value("11"))
            .andExpect(jsonPath("$.user.name").value("user 1"));
    }

    @Test
    void testLogin_IncorrectLogin() throws Exception {
        // Arrange
        when(sessionsService.login(any(LoginRequest.class))).thenThrow(new LoginFailedException());

        // Act & Assert
        mockMvc.perform(post("/session/login")
            .content("{\"username\":\"username\",\"password\":\"password\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Incorrect username or password"));
    }

    @Test
    void testLogout_Success() throws Exception {
        // Arrange
        when(sessionsService.logout(anyString())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/session/logout")
            .header("X-Session-Id", "session1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Logged out successfully."));
    }

    @Test
    void testLogout_Failed() throws Exception {
        // Arrange
        when(sessionsService.logout(anyString())).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/session/logout")
            .header("X-Session-Id", "session1"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Invalid session."));
    }

    @Test
    void testLogout_NoSessionHeader() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/session/logout"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Missing session id"));
        
        // Should throw before reaching logout
        verify(sessionsService, times(0)).logout(anyString());
    }

    @Test
    void testLogout_InvalidSession() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/session/logout")
            .header("X-Session-Id", "session3"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Invalid session"));

        // Should throw before reaching logout
        verify(sessionsService, times(0)).logout(anyString());
    }

    @Test
    void testLogout_ExpiredSession() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/session/logout")
            .header("X-Session-Id", "session2"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Expired session"));

        // Should throw before reaching logout
        verify(sessionsService, times(0)).logout(anyString());
    }
}
