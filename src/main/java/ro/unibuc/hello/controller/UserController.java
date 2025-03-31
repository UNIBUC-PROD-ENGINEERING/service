package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;
import ro.unibuc.hello.dto.response.UserListDto;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/all")
    public UserListDto getAll() {return userService.getAll();}

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/me")
    public UserDto getSelf() {return userService.getSelf();}

    @Secured("ROLE_ADMIN")
    @PutMapping("/{username}")
    public UserDto updateUser(@PathVariable String username, @RequestBody @Valid RegisterDto registerDto) {return userService.updateUser(username, registerDto);}

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path="/{username}")
    public void delete (@PathVariable String username) {
        userService.delete(username);
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping(path="/search")
    public UserListDto searchUsers(@RequestParam String keyword){return userService.getRelevantUsers(keyword);}
    
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping(path="/{username}")
    public UserDto getUser(@PathVariable String username) {return userService.getUser(username);}
    
}
