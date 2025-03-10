package main.java.ro.unibuc.hello.controller;

import main.java.ro.unibuc.hello.entity.Client;
import main.java.ro.unibuc.hello.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Client client, @RequestParam String password, @RequestParam String email) {
        return clientService.register(client, password, email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        return clientService.login(email, password);
    }
}
