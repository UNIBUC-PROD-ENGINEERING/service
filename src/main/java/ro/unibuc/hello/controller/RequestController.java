package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.ItemDto;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;
import ro.unibuc.hello.dto.response.UserListDto;

@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private final ToDoService toDoService;

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/create")
    public boolean Create(String toDoList, String text) { return toDoService.createRequest(toDoList, text); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/accept")
    public boolean Accept(String username, String toDoList) { return toDoService.acceptRequest(username, toDoList); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/deny")
    public boolean Deny(String username, String toDoList) { return toDoService.denyRequest(username, toDoList); }
}
