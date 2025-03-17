package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.entity.Group;
import ro.unibuc.hello.service.GroupService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public Optional<Group> getGroupById(@PathVariable String id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    @PutMapping("/{groupId}/add-client/{clientId}")
    public Group addClientToGroup(@PathVariable String groupId, @PathVariable String clientId) {
        return groupService.addClientToGroup(groupId, clientId);
    }

    @PutMapping("/{groupId}/add-transaction/{transactionId}")
    public Group addTransactionToGroup(@PathVariable String groupId, @PathVariable String transactionId) {
        return groupService.addTransactionToGroup(groupId, transactionId);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable String id) {
        groupService.deleteGroup(id);
    }
}
