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
import ro.unibuc.hello.dto.AuctionWithItem;
import ro.unibuc.hello.dto.BidWithAuction;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.UserPostRequest;
import ro.unibuc.hello.permissions.UserPermissionChecker;
import ro.unibuc.hello.service.UsersService;


@Controller
public class UsersController {

    @Autowired
    private UsersService userService;

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
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PublicEndpoint
    @GetMapping("/users/{id}/items")
    @ResponseBody
    public List<Item> getUserItems(@PathVariable String id) {
        return userService.getUserItems(id);
    }

    @PublicEndpoint
    @GetMapping("/users/{id}/auctions")
    @ResponseBody
    public List<AuctionWithItem> getUserAuctions(@PathVariable String id) {
        return userService.getUserAuctions(id);
    }

    @PublicEndpoint
    @GetMapping("/users/{id}/bids")
    @ResponseBody
    public List<BidWithAuction> getUserBids(@PathVariable String id) {
        return userService.getUserBids(id);
    }

    @PostMapping("/users")
    @ResponseBody
    public User createUser(@RequestBody UserPostRequest user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    @ResponseBody
    public User updateUser(HttpServletRequest request, @PathVariable String id, @RequestBody UserPostRequest user) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        permissionChecker.checkOwnership(userId, id);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public void deleteUser(HttpServletRequest request, @PathVariable String id) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        permissionChecker.checkOwnership(userId, id);
        userService.deleteUser(id);
    }
}
