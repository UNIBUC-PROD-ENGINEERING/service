package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.FriendRequestEntity;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.service.FriendRequestService;

import java.util.List;
import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;


@RestController
@RequestMapping("/friends")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping("/send")
    public ResponseEntity<?> sendRequest(@RequestParam String fromUserId, @RequestParam String toUserId) {
        try {
            return ResponseEntity.ok(friendRequestService.sendRequest(fromUserId, toUserId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/requests")
    public List<FriendRequestEntity> getRequests(@RequestParam String toUserId) {
        return friendRequestService.getReceivedRequests(toUserId);
    }

    @PostMapping("/respond")
    public String respond(@RequestParam String requestId, @RequestParam boolean accept) {
        boolean success = friendRequestService.respondToRequest(requestId, accept);
        if (success) {
            return accept ? "Friend request accepted." : "Friend request rejected.";
        } else {
            return "Friend request cannot be modified (maybe already responded or not found).";
        }
    }

    @GetMapping("/friends")
    public ResponseEntity<?> getFriends(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(friendRequestService.getFriendsOfUser(userId));
        } catch (EntityNotFoundException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFriend(@RequestParam String userId1, @RequestParam String userId2) {
        boolean removed = friendRequestService.removeFriend(userId1, userId2);
        if (removed) {
            return ResponseEntity.ok("Friend removed successfully.");
        } else {
            return ResponseEntity.badRequest().body("No friendship found between users.");
        }
    }


}
