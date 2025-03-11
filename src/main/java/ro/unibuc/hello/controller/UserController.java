package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.service.UsersService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return usersService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        Optional<User> user = usersService.getUserById(id);
        return user.orElse(null); // Return null if not found, Spring will send 404
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return usersService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        boolean isDeleted = usersService.deleteUser(id);
        return isDeleted ? "User deleted" : "User not found";
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return usersService.getUserByEmail(email);
    }
}
