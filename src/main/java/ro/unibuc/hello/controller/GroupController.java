package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.entity.Group;
import ro.unibuc.hello.entity.Transaction;
import ro.unibuc.hello.service.GroupService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}") 
    public ResponseEntity<Group> getGroup(@PathVariable String id, 
                                          @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group,
                                             @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(groupService.createGroup(authHeader, group));
    }

    @PutMapping("/{id}/invite/{userId}")
    public ResponseEntity<String> inviteUser(@PathVariable String id,
                                             @PathVariable String userId,
                                             @RequestHeader("Authorization") String authHeader) {
        groupService.inviteUser(authHeader, id, userId);
        return ResponseEntity.ok("Invitation sent to user " + userId);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<String> acceptInvite(@PathVariable String id,
                                               @RequestHeader("Authorization") String authHeader) {
        groupService.acceptInvite(authHeader, id);
        return ResponseEntity.ok("User joined the group");
    }

   @PostMapping("/{id}/transactions")
    public ResponseEntity<String> addTransactionToGroup(@PathVariable String id,
                                                    @RequestHeader("Authorization") String authHeader,
                                                    @RequestBody Map<String, String> requestBody) {
    String transactionId = requestBody.get("transactionId");
    groupService.addTransactionToGroup(authHeader, id, transactionId);
    return ResponseEntity.ok("Transaction " + transactionId + " added to group " + id);
}

    @PutMapping("/{id}/remove/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable String id,
                                             @PathVariable String userId,
                                             @RequestHeader("Authorization") String authHeader) {
        groupService.removeUser(authHeader, id, userId);
        return ResponseEntity.ok("User removed from group");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable String id,
                                              @RequestHeader("Authorization") String authHeader) {
        groupService.deleteGroup(authHeader, id);
        return ResponseEntity.ok("Group deleted successfully");
    }
}
