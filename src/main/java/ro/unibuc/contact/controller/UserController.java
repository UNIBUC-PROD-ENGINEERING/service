package ro.unibuc.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ro.unibuc.contact.dto.CreateUserResponse;
import ro.unibuc.contact.service.UserService;
import ro.unibuc.contact.data.UserEntity;
import ro.unibuc.contact.exception.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class UserController{

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        Optional<UserEntity> existingUser = userService.findByUsername(user.username);
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
       
        UserEntity newUser;
        try {
            newUser = userService.createUser(user);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
        CreateUserResponse response = new CreateUserResponse(newUser.id, "User created successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
         try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

