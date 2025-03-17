package ro.unibuc.hello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.UserRepository;
import ro.unibuc.hello.data.UserWithPartiesResponse;
import ro.unibuc.hello.dto.PartySummaryDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;

    public UserController(UserRepository userRepository, PartyRepository partyRepository) {
        this.userRepository = userRepository;
        this.partyRepository = partyRepository;
    }

    // Get all users
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID and their parties
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserWithParties(@PathVariable String userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UserEntity user = userOptional.get();

        // Get all parties the user is in
        List<PartySummaryDTO> userParties = partyRepository.findByUserIdsContaining(userId)
            .stream()
            .map(party -> new PartySummaryDTO(party.getId(), party.getName(), party.getDate()))
            .collect(Collectors.toList());

        // Create a response with only selected user and party details
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("points", user.getPoints());
        response.put("parties", userParties);

        return ResponseEntity.ok(response);
    }

    // Create a new user
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }

    // Update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String userId, @RequestBody UserEntity updatedUser) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        updatedUser.setId(userId);
        return ResponseEntity.ok(userRepository.save(updatedUser));
    }

    // Delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
    
    // Add user to a party
    @PostMapping("/{userId}/join/{partyId}")
    public ResponseEntity<?> addUserToParty(@PathVariable String userId, @PathVariable String partyId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<PartyEntity> partyOptional = partyRepository.findById(partyId);

        if (userOptional.isEmpty() || partyOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User or Party not found");
        }

        PartyEntity party = partyOptional.get();
        if (!party.getUserIds().contains(userId)) {
            party.getUserIds().add(userId);
            partyRepository.save(party);
        }

        return ResponseEntity.ok(party);
    }

     @PostMapping("/{userId}/createParty")
    public ResponseEntity<?> createParty(@PathVariable String userId, @RequestBody PartyEntity party) {
        // Check if the user exists
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Set the user as the party creator
        party.setUserIds(new ArrayList<>(List.of(userId))); // Ensure user is added

        // Save the party
        PartyEntity savedParty = partyRepository.save(party);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedParty);
    }

}
