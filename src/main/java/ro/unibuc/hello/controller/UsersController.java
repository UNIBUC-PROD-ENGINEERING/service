package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import ro.unibuc.hello.auth.AuthUtil;
import ro.unibuc.hello.auth.PublicEndpoint;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserDetails;
import ro.unibuc.hello.dto.UserPostRequest;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.permissions.UserPermissionChecker;
import ro.unibuc.hello.service.UserService;


@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPermissionChecker permissionChecker;

    @PublicEndpoint
    @GetMapping("/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PublicEndpoint
    @GetMapping("/users/{id}")
    @ResponseBody
    public UserDetails getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    @ResponseBody
    public UserDetails createUser(@RequestBody UserPostRequest user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    @ResponseBody
    public UserDetails updateUser(HttpServletRequest request, @PathVariable String id, @RequestBody UserPostRequest user) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        permissionChecker.checkOwnership(userId, id);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public void deleteUser(HttpServletRequest request, @PathVariable String id) throws EntityNotFoundException {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        permissionChecker.checkOwnership(userId, id);
        userService.deleteUser(id);
    }
}
