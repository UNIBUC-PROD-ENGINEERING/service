package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.CarsDTO;
import ro.unibuc.hello.dto.RegisterUserDTO;
import ro.unibuc.hello.dto.UserDTO;
import ro.unibuc.hello.service.UserService;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/getAll")
    @ResponseBody
    public List<UserEntity> getUsers(){
        List<UserEntity> users = userService.getUsers();
        return users;
    }

    @GetMapping("/user/get")
    @ResponseBody
    public UserDTO getCar(@RequestParam(name="userId") String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user/register")
    @ResponseBody
    public RegisterUserDTO registerUser(@RequestParam(name="firstName") String firstName,
                                        @RequestParam(name="lastName") String lastName,
                                        @RequestParam(name="userName") String userName,
                                        @RequestParam(name="password") String password) {
        return userService.registerUser(firstName, lastName, userName, password);
    }

    @PutMapping("/user/changeUsername")
    @ResponseBody
    public void changeUsername(@RequestParam(name="id") String id,
                             @RequestParam(name="userName") String userName) {

        userService.changeUsername(id, userName);
    }

    @DeleteMapping("/user/delete")
    @ResponseBody
    public void deleteUser(@RequestParam(name="carId") String id) {
        userService.deleteUserById(id);
    }
}
