package ro.unibuc.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.controllers.contracts.UserCreateRequest;
import ro.unibuc.hello.dtos.UserDTO;
import ro.unibuc.hello.services.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public UserDTO getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    @ResponseBody
    public UserDTO addUser(@RequestBody UserCreateRequest user) {
        return userService.addUser(user);
    }

    @PutMapping("/users/{id}")
    @ResponseBody
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserCreateRequest user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PostMapping("/users/{id}/policies")
    @ResponseBody
    public UserDTO addPolicyToUser(@PathVariable String id, @RequestBody List<String> policyIds) {
        return userService.addPoliciesToUser(id, policyIds);
    }
}
