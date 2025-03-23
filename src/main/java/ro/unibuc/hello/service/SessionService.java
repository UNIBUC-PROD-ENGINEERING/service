package ro.unibuc.hello.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.SessionEntity;
import ro.unibuc.hello.data.SessionRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.Session;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.ExpiredSessionException;
import ro.unibuc.hello.exception.InvalidSessionException;
import ro.unibuc.hello.exception.LoginFailedException;

@Component
public class SessionService {
    static private final int sessionExpireTime = 30;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public Session login(LoginRequest loginReq) {
        UserEntity user = userRepository.findByUsername(loginReq.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(loginReq.getUsername()));

        if (user != null && user.getPassword().equals(loginReq.getPassword())) {
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(sessionExpireTime);
            SessionEntity session = new SessionEntity(user, expiresAt);
            session = sessionRepository.save(session);
            User userData = new User(session.getUser());
            return new Session(session.getId(), userData);
        }

        throw new LoginFailedException();
    }

    public ResponseEntity<String> logout() {
        // SessionEntity session = sessionRepository.findById(logoutReq.getSessionId()).orElseThrow(() -> new InvalidSessionException());
        // sessionRepository.delete(session);
        return new ResponseEntity<>("Logout successfull", HttpStatus.OK);
    }

    public SessionEntity getValidSession(String sessionId) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(() -> new InvalidSessionException());

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredSessionException();
        }

        return session;
    }
}
