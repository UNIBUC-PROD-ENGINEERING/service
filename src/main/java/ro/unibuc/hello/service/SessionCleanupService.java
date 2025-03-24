package ro.unibuc.hello.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.data.SessionRepository;

@Service
public class SessionCleanupService {

    @Autowired
    private SessionRepository sessionRepository;

    @Scheduled(fixedRate = 360000) // Runs every hour
    public void cleanupExpiredSessions() {
        sessionRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
