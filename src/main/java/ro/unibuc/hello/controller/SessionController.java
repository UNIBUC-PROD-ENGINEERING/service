package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.unibuc.hello.auth.PublicEndpoint;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.Session;
import ro.unibuc.hello.service.SessionService;


@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PublicEndpoint
    @PostMapping("/login")
    public Session getMethodName(@RequestBody LoginRequest loginReq) {
        return sessionService.login(loginReq);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("X-Session-Id") String sessionId) {
        boolean success = sessionService.logout(sessionId);

        if (success) {
            return ResponseEntity.ok("Logged out successfully.");
        } else {
            return ResponseEntity.status(401).body("Invalid session.");
        }
    }
}
