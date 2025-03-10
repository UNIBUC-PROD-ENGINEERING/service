package main.java.ro.unibuc.hello.service;

import main.java.ro.unibuc.hello.entity.Client;
import main.java.ro.unibuc.hello.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> register(Client client, String password, String email) {
        if (clientRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Client with this email already exists");
        }
        
        String hashedPassword = passwordEncoder.encode(password);
        client.setPassword(hashedPassword);
        client.setEmail(email);
        clientRepository.save(client);
        return ResponseEntity.ok("Client registered successfully");
    }

    public ResponseEntity<?> login(String email, String password) {
        Optional<Client> clientOpt = clientRepository.findByEmail(email);
        if (clientOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Client not found");
        }
        
        Client client = clientOpt.get();
        if (!passwordEncoder.matches(password, client.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        
        return ResponseEntity.ok("Login successful");
    }
}
