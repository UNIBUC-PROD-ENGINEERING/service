package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.UserDTO;
import ro.unibuc.hello.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    @ResponseBody
    public UserDTO getUserById(@PathVariable long id){
        return userService.findUserById(id);
    }

    @GetMapping("/user")
    @ResponseBody
    public List<UserDTO> getAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping("/user")
    @ResponseBody
    public String createUser(@RequestBody UserDTO userDTO){
        if(userService.createUser(userDTO)){
            return "Success";
        }
        return "Create failed";
    }

    @PutMapping("/user/{id}")
    @ResponseBody
    public String updateUser(@PathVariable long id, @RequestBody UserDTO userDTO){
        if(userService.updateUser(userDTO)){
            return "Success";
        }
        return "Update failed";
    }

    @DeleteMapping("/user/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable long id){
        if(userService.deleteUser(id)){
            return "Success";
        }
        return "Delete failed";
    }

    @GetMapping("/user/name/{name}")
    @ResponseBody
    public List<UserDTO> findUsersByName(@PathVariable String name){
        return userService.findUsersByName(name);
    }

}


