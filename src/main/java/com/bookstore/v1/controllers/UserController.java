package com.bookstore.v1.controllers;

import com.bookstore.v1.data.User;
import com.bookstore.v1.services.UserService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    MeterRegistry metricsRegistry;

    @GetMapping("/user/getAll")
    @ResponseBody
    @Timed(value = "user.getUsers.time", description = "Time taken to return users")
    @Counted(value = "user.getUsers.count", description = "Times users were returned")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/get")
    @ResponseBody
    @Timed(value = "user.getUser.time", description = "Time taken to return an user")
    @Counted(value = "user.getUser.count", description = "Times a user was returned")
    public UserDTO getUser(@RequestParam(name="id") String id) {
        return userService.getUser(id);
    }

    @PostMapping("/user/insert")
    @ResponseBody
    @Timed(value = "user.insertUser.time", description = "Time taken to insert an insert")
    @Counted(value = "user.insertUser.count", description = "Times a insert was inserted")
    public UserDTO insertUser(
            @RequestParam(name="userName") String userName,
            @RequestParam(name="email") String email,
            @RequestParam(name="phoneNumber") String phoneNumber,
            @RequestParam(name="reviewIds") List<String> reviewIds,
            @RequestParam(name="wishlistIds") List<String> wishlistIds
    ) {
        return userService.insertUser(userName, email, phoneNumber, reviewIds, wishlistIds);
    }

    @PutMapping("/user/update")
    @ResponseBody
    @Timed(value = "user.updateUser.time", description = "Time taken to update an user")
    @Counted(value = "user.updateUser.count", description = "Times a user was updated")
    public UserDTO updateUser(
            @RequestParam(name="id") String id,
            @RequestParam(name="userName", required = false) String userName,
            @RequestParam(name="email", required = false) String email,
            @RequestParam(name="phoneNumber", required = false) String phoneNumber,
            @RequestParam(name="reviewIds") List<String> reviewIds,
            @RequestParam(name="wishlistIds") List<String> wishlistIds
    ) {
        return userService.updateUser(id, userName, email, phoneNumber, reviewIds, wishlistIds);
    }

    @DeleteMapping("/user/delete")
    @ResponseBody
    @Timed(value = "user.deleteUser.time", description = "Time taken to delete an user")
    @Counted(value = "user.deleteUser.count", description = "Times a user was deleted")
    public String deleteUser(@RequestParam(name="id") String id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/user/bugGenerator")
    @ResponseBody
    @Timed(value = "user.bugGenerator.time", description = "Time taken to bug an insert")
    @Counted(value = "user.bugGenerator.count", description = "Times a bug was inserted")
    public UserDTO buggedInsertUser(
            @RequestParam(name="userName") String userName,
            @RequestParam(name="email") String email,
            @RequestParam(name="phoneNumber") String phoneNumber,
            @RequestParam(name="reviewIds") List<String> reviewIds,
            @RequestParam(name="wishlistIds") List<String> wishlistIds
    ) {
        return userService.buggedInsertUser(userName, email, phoneNumber, reviewIds, wishlistIds);
    }

}
