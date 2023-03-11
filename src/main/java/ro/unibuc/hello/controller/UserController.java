package ro.unibuc.hello.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.UserDTO;
import ro.unibuc.hello.service.UserService;


import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    @ResponseBody
    public UserDTO getuser(@RequestParam(name="id", required=false) String id) {
        return userService.getUser(id);
    }
    @PostMapping("/createUser")
    @ResponseBody
    public void createuser(UserDTO user) {
        userService.createUser(user);
    }
    @GetMapping("/getAllUsers")
    @ResponseBody
    public List<UserDTO> getAll() {
        return userService.getAll();
    }
    @PutMapping("/putUser")
    @ResponseBody
    public boolean updateuser(UserDTO user) {
        return userService.updateuser(user);
    }

    @DeleteMapping("/deleteUser")
    @ResponseBody
    public boolean deleteuser(String id) {
        return userService.deleteuser(id);
    }
}