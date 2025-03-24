package ro.unibuc.hello.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.SessionRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.Session;
import ro.unibuc.hello.exception.InvalidSessionException;
import ro.unibuc.hello.exception.LoginFailedException;

class SessionsServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionsService sessionsService = new SessionsService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        String username = "username";
        LoginRequest loignReq = new LoginRequest(username, "password1");
        UserEntity user = new UserEntity("11", "user 1", "password1", username);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(100);
        SessionEntity sessionEntity = new SessionEntity("41", "session1", user, expiresAt);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(sessionRepository.save(any(SessionEntity.class))).thenReturn(sessionEntity);

        // Act
        Session session = sessionsService.login(loignReq);

        // Assert
        assertEquals("session1", session.getSessionId());
        assertEquals("11", session.getUser().getId());
        assertEquals("user 1", session.getUser().getName());
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        String username = "username";
        LoginRequest loignReq = new LoginRequest(username, "password1");
        UserEntity user = new UserEntity("11", "user 1", "password1", username);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(100);
        SessionEntity sessionEntity = new SessionEntity("41", "session1", user, expiresAt);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(sessionRepository.save(any(SessionEntity.class))).thenReturn(sessionEntity);

        // Act & Assert
        assertThrows(LoginFailedException.class, () -> sessionsService.login(loignReq));
    }

    @Test
    void testLogin_InvalidPassword() {
        // Arrange
        String username = "username";
        LoginRequest loignReq = new LoginRequest(username, "password2");
        UserEntity user = new UserEntity("11", "user 1", "password1", username);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(100);
        SessionEntity sessionEntity = new SessionEntity("41", "session1", user, expiresAt);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(sessionRepository.save(any(SessionEntity.class))).thenReturn(sessionEntity);

        // Act & Assert
        assertThrows(LoginFailedException.class, () -> sessionsService.login(loignReq));
    }

    @Test
    void testLogout_Success() {
        // Arrange
        String sessionId = "session1";
        UserEntity user = new UserEntity("11", "user 1", "password1", "username1");
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(100);
        SessionEntity sessionEntity = new SessionEntity("41", sessionId, user, expiresAt);

        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(sessionEntity));
        
        // Act
        boolean success = sessionsService.logout(sessionId);

        // Assert
        assertTrue(success);
        verify(sessionRepository, times(1)).delete(sessionEntity);
    }

    @Test
    void testLogout_SessionNotFound() {
        // Arrange
        String sessionId = "session1";
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());
        
        // Act
        boolean success = sessionsService.logout(sessionId);

        // Assert
        assertFalse(success);
    }

    @Test
    void testGetValidSession_Success() {
        // Arrange
        String sessionId = "session1";
        UserEntity user = new UserEntity("11", "user 1", "password1", "username1");
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(100);
        SessionEntity sessionEntity = new SessionEntity("41", sessionId, user, expiresAt);

        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(sessionEntity));

        // Act
        SessionEntity returedSession = sessionsService.getValidSession(sessionId);

        // Assert
        assertEquals(returedSession.getId(), sessionEntity.getId());
        assertEquals(returedSession.getSessionId(), sessionEntity.getSessionId());
        assertEquals(returedSession.getUser().getId(), sessionEntity.getUser().getId());
    }

    @Test
    void testGetValidSession_SessionNotFound() {
        // Arrange
        String sessionId = "session1";
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

        // Act
        InvalidSessionException ex = assertThrows(InvalidSessionException.class, () -> sessionsService.getValidSession(sessionId));

        // Assert
        assertEquals("Invalid session", ex.getMessage());
    }

    @Test
    void testGetValidSession_ExpiredSession() {
        // Arrange
        String sessionId = "session1";
        UserEntity user = new UserEntity("11", "user 1", "password1", "username1");
        LocalDateTime expiresAt = LocalDateTime.now().minusSeconds(1);
        SessionEntity sessionEntity = new SessionEntity("41", sessionId, user, expiresAt);
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(sessionEntity));

        // Act
        InvalidSessionException ex = assertThrows(InvalidSessionException.class, () -> sessionsService.getValidSession(sessionId));

        // Assert
        assertEquals("Expired session", ex.getMessage());
    }
}
