package ro.unibuc.hello.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.UserEntity;

public class SessionTest {
    Session session = new Session("session1", new User("11", "user 1"));

    UserEntity user = new UserEntity("11", "user 1", "password1", "username1");
    LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(100);
    SessionEntity sessionEntity = new SessionEntity("41", "session1", user, expiresAt);
    Session sessionFromEntity = new Session(sessionEntity);

    Session emptySession = new Session();


    @Test
    void testGetSessionId() {
        assertEquals("session1", session.getSessionId());
        assertEquals("session1", sessionFromEntity.getSessionId());
    }

    @Test
    void testGetUser() {
        assertEquals("11", session.getUser().getId());
        assertEquals("11", sessionFromEntity.getUser().getId());
    }

    @Test
    void testSetSessionId() {
        emptySession.setSessionId("session2");
        assertEquals("session2", emptySession.getSessionId());
    }

    @Test
    void testSetUser() {
        emptySession.setUser(new User("12", "user 2"));
        assertEquals("12", emptySession.getUser().getId());
    }
}
