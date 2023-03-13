package com.bookstore.v1.controllers;

import com.bookstore.v1.dto.UserDTO;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    @ResponseBody
    public UserDTO addUser(@RequestBody UserDTO userDTO) throws EmptyFieldException {
        return userService.addUser(userDTO);
    }

    @PutMapping("/update-user")
    @ResponseBody
    public UserDTO updateUser(@RequestBody UserDTO userUpdateDTO) throws EmptyFieldException, EntityNotFoundException {
        return userService.updateUser(userUpdateDTO);
    }

    @DeleteMapping("/delete-user/{userId}")
    @ResponseBody
    public void deleteUser(@PathVariable String userId) throws EntityNotFoundException {
        userService.deleteUserById(userId);
    }

    @GetMapping("/get-user/{userId}")
    @ResponseBody
    public UserDTO getUserById(@PathVariable String userId) throws EntityNotFoundException {
        return userService.getUserById(userId);
    }

    @GetMapping("/get-users")
    @ResponseBody
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

}
