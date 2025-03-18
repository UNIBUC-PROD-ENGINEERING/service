package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.request.*;
import ro.unibuc.hello.dto.response.*;

@RestController
@AllArgsConstructor
@RequestMapping("/todo_list")
public class ToDoListController {
    @Autowired
    private final ToDoService toDoService;

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/create")
    public boolean Create(String name, String description) { return toDoService.createToDoList(name, description); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/update")
    public boolean Update(String oldName, String name, String description) { return toDoService.updateToDoList(oldName, name, description); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/delete")
    public boolean Delete(String name) { return toDoService.deleteToDoList(name); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/all")
    public ToDoListCollectionDto GetMyLists() { return toDoService.getMyToDoLists(); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/members")
    public UserListDto GetMembers(String name) { return toDoService.getMembersToDoList(name); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/pending")
    public RequestListDto GetRequests(String name) { return toDoService.getRequestsToDoList(name); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/items")
    public ItemListDto GetItems(String name) { return toDoService.getItemsToDoList(name); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/leave")
    public boolean Leave(String name) { return toDoService.leaveToDoList(name); }
}
