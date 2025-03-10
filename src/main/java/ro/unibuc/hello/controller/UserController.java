package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;
import ro.unibuc.hello.dto.response.UserListDto;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;


    //TODO: login functionality

    // @PostMapping("/login")

    // public UserDto login(@RequestBody @Valid LoginDto loginDto) {return userService.login(loginDto);}
    
    // @PostMapping("/logout")
    // public void logout(){userService.logout();}

    @PostMapping("/register")
    @ResponseBody
    public UserDto register(@RequestBody @Valid RegisterDto registerDto) {return userService.createUser(registerDto);}

    @GetMapping("/all")
    public UserListDto getAll() {return userService.getAll();}

    @PutMapping("/{username}")
    @ResponseBody
    public UserDto updateUser(@PathVariable String username, @RequestBody @Valid RegisterDto registerDto) {return userService.updateUser(username, registerDto);}

    @DeleteMapping(path="/{username}")
    @ResponseBody
    public void delete (@PathVariable String username) {
        userService.delete(username);
    }
    
}
