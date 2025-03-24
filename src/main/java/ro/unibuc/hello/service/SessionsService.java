package ro.unibuc.hello.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.SessionRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.Session;
import ro.unibuc.hello.exception.InvalidSessionException;
import ro.unibuc.hello.exception.LoginFailedException;

@Component
public class SessionsService {
    static private final int sessionExpireTime = 30;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public Session login(LoginRequest loginReq) {
        UserEntity user = userRepository.findByUsername(loginReq.getUsername())
            .orElseThrow(() -> new LoginFailedException());

        if (user != null && user.getPassword().equals(loginReq.getPassword())) {
            String sessionId = generateSessionId();
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(sessionExpireTime);

            SessionEntity session = new SessionEntity(sessionId, user, expiresAt);
            session = sessionRepository.save(session);
            return new Session(session);
        }

        throw new LoginFailedException();
    }

    public boolean logout(String sessionId) {
        Optional<SessionEntity> sessionOpt = sessionRepository.findBySessionId(sessionId);

        if (sessionOpt.isPresent()) {
            sessionRepository.delete(sessionOpt.get());
            return true;
        }
        
        return false;
    }

    public SessionEntity getValidSession(String sessionId) {
        SessionEntity session = sessionRepository.findBySessionId(sessionId)
            .orElseThrow(() -> new InvalidSessionException("Invalid session"));

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            sessionRepository.delete(session); // Remove expired session
            throw new InvalidSessionException("Expired session");
        }

        return session;
    }

    private String generateSessionId() {
        return java.util.UUID.randomUUID().toString();
    }
}
