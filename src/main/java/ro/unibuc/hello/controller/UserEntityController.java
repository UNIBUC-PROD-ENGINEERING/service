package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.UserEntity;
@RestController
@RequestMapping("/users")
public class UserEntityController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
