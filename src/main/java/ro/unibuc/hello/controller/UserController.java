package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.UsersService;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.data.UserEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public User createUser(@RequestBody UserEntity user) {
        return usersService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return usersService.getUserById(id).orElse(null); 
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody UserEntity user) {
        return usersService.updateUser(id, user).orElse(null); 
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        boolean isDeleted = usersService.deleteUser(id);
        return isDeleted ? "User deleted" : "User not found";
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return usersService.getUserByEmail(email).orElse(null);
    }
    @GetMapping("/fullName/{fullName}")
    public User getUserByFullName(@PathVariable String fullName) {
        return usersService.getUserByFullName(fullName).orElse(null);
    }
}
