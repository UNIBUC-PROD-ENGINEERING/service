package ro.unibuc.hello.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.hello.dto.security.AuthRequestDTO;
import ro.unibuc.hello.dto.security.JwtAuthResponseDTO;
import ro.unibuc.hello.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.login(authRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.register(authRequestDTO));
    }
}
