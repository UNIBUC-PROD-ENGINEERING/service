package ro.unibuc.prodeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ro.unibuc.prodeng.request.ChangeNameRequest;
import ro.unibuc.prodeng.request.LoginRequest;
import ro.unibuc.prodeng.request.RegisterRequest;
import ro.unibuc.prodeng.response.LoginResponse;
import ro.unibuc.prodeng.response.UserResponse;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest req){
        return userService.registerUser(req);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req){
        return userService.login(req);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) throws EntityNotFoundException {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // @PostMapping
    // public ResponseEntity<UserResponse> createUser(@Valid @RequestBody RegisterRequest request) {
    //     UserResponse user = userService.registerUser(request);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(user);
    // }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String id,
            @Valid @RequestBody ChangeNameRequest request) throws EntityNotFoundException {
        UserResponse user = userService.changeName(id, request.name());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<UserResponse> changeName(
            @PathVariable String id,
            @Valid @RequestBody ChangeNameRequest request) throws EntityNotFoundException {
        UserResponse user = userService.changeName(id, request.name());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) throws EntityNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email)
            throws EntityNotFoundException {
        UserResponse user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}
