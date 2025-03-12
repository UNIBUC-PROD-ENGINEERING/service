package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.UserService;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable String id) throws EntityNotFoundException {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    @ResponseBody
    public User updateUser(@PathVariable String id, @RequestBody User user) throws EntityNotFoundException {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable String id) throws EntityNotFoundException {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session) {
        User existingUser = userService.getUserById(user.getId());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("loggedUser", existingUser);
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logout successful";
    }

    @GetMapping("/loggedUser")
    @ResponseBody
    public User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }
}
