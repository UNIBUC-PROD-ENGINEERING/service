package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserDetails;
import ro.unibuc.hello.dto.UserPost;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;

import ro.unibuc.hello.service.UserService;


@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    
    @GetMapping("/users/{id}")
    @ResponseBody
    public UserDetails getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }


    @PostMapping("/users")
    @ResponseBody
    public UserDetails createUser(@RequestBody UserPost user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    @ResponseBody
    public UserDetails updateUser(@PathVariable String id, @RequestBody UserPost user) throws EntityNotFoundException {
        return userService.updateUser(id, user);  //itemurile precizate sunt adaugate la cele existente pt userul user 
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable String id) throws EntityNotFoundException {
          userService.deleteUser(id);
    }
}
