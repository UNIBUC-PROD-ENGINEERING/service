package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;



import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.service.UserService;
import org.springframework.http.HttpStatus;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
  
    @GetMapping("/get-user/{id}")
    @ResponseBody
    public UserEntity getUser(@PathVariable String id){
       try {
           return userService.getUser(id);
       } catch (Exception e) {
           return null;   
       }
    }

    @PostMapping("/addNewUser")
    @ResponseBody
    public String addUser(@RequestBody UserDto userDto){  
        try{
            return userService.addUser(userDto);
        }
        catch (Exception e)
        {
                throw e;
        }

    }


    @DeleteMapping("/deleteUser/{id}")
    @ResponseBody
    public void deleteUserById(@PathVariable String id) {
        try {
            userService.deleteUserById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}
