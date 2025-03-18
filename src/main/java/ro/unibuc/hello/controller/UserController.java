package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    private User convertToDto(UserEntity entity) {
        return new User(entity.getId(), entity.getNume(), entity.getPrenume(), entity.getEmail());
    }
    
    @GetMapping("/api/user")
    public List<User> getAllUseri(){
        return userService.getAllUseri()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        return userService.getUserById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/user")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) throws EntityNotFoundException {
        UserEntity userEntity = new UserEntity(
                user.getId(),
                user.getNume(),
                user.getPrenume(),
                user.getEmail()
        );
        
        Optional<UserEntity> updatedEntity = userService.updateUser(id, userEntity);
        
        return updatedEntity.map(entity -> ResponseEntity.ok(convertToDto(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
