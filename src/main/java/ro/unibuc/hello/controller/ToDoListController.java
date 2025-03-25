package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.response.*;

@RestController
@AllArgsConstructor
@RequestMapping("/todo_list")
public class ToDoListController {
    @Autowired
    private final ToDoService toDoService;

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/create")
    public ToDoListResponseDto Create(@RequestBody @Valid ToDoListResponseDto ToDoListResponseDto) { return toDoService.createToDoList(ToDoListResponseDto); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PutMapping("/update/{toDoListName}")
    public ToDoListResponseDto Update(@RequestBody @Valid ToDoListResponseDto ToDoListResponseDto, @PathVariable String toDoListName) { return toDoService.updateToDoList(ToDoListResponseDto, toDoListName); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @DeleteMapping("/delete/{name}")
    public boolean Delete(@PathVariable String name) { return toDoService.deleteToDoList(name); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/all")
    public ToDoListCollectionDto GetMyLists() { return toDoService.getMyToDoLists(); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/members")
    public UserListDto GetMembers(@RequestBody String name) { return toDoService.getMembersToDoList(name); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/pending")
    public RequestListDto GetRequests(@RequestBody String name) { return toDoService.getRequestsToDoList(name); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/items")
    public ItemListDto GetItems(@RequestBody String name) { return toDoService.getItemsToDoList(name); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @DeleteMapping("/leave")
    public boolean Leave(@RequestBody String name) { return toDoService.leaveToDoList(name); }
}
