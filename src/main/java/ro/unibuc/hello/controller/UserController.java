package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;
import ro.unibuc.hello.dto.response.UserListDto;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/all")
    public UserListDto getAll() {return userService.getAll();}

    @GetMapping("/me")
    public UserDto getSelf() {return userService.getSelf();}

    @PutMapping("/{username}")
    public UserDto updateUser(@PathVariable String username, @RequestBody @Valid RegisterDto registerDto) {return userService.updateUser(username, registerDto);}

    @DeleteMapping(path="/{username}")
    public void delete (@PathVariable String username) {
        userService.delete(username);
    }
    
    
}
