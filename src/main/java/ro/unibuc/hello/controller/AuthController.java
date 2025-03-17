package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.ClientService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ClientService clientService;

    @Autowired
    public AuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String token = clientService.loginClient(credentials.get("email"), credentials.get("password"));
        return Map.of("token", token);
    }
}
