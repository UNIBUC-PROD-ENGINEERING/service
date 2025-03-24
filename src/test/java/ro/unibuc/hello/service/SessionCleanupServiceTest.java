package ro.unibuc.hello.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.unibuc.hello.data.SessionRepository;

public class SessionCleanupServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionCleanupService sessionCleanupService = new SessionCleanupService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCleanupExpiredSessions() {
        // Act
        sessionCleanupService.cleanupExpiredSessions();

        // Assert
        verify(sessionRepository, times(1)).deleteByExpiresAtBefore(any(LocalDateTime.class));
    }
}
