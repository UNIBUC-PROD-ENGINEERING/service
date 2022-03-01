package ro.unibuc.hello.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/getAll")
    @ResponseBody
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/get")
    @ResponseBody
    public UserEntity getUser(@RequestParam(name="id") String id) {
        return userRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
    }

    @PostMapping("/user/insert")
    @ResponseBody
    public UserEntity insertUser(@RequestParam(name="name") String name, @RequestParam(name="email") String email) {
        return userRepository.save(new UserEntity(name, email));
    }

    @PutMapping("/user/update")
    @ResponseBody
    public UserEntity updateUser(@RequestParam(name="id") String id, @RequestParam(name="name") String name, @RequestParam(name="email") String email) {
        UserEntity user = userRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(user != null) {
            if(name != null)
                user.setName(name);
            if(email != null)
                user.setEmail(email);
            return userRepository.save(user);
        } else
            return  null;
    }

    @DeleteMapping("/user/delete")
    @ResponseBody
    public void deleteUser(@RequestParam(name="id") String id) {
        userRepository.deleteById(String.valueOf(new ObjectId(id)));
    }

}
