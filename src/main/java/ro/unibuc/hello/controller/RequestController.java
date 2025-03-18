package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.request.RequestDto;
import ro.unibuc.hello.dto.response.RequestResponseDto;

@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private final ToDoService toDoService;

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/create")
    public RequestResponseDto Create(@RequestBody @Valid RequestDto requestDto) { return toDoService.createRequest(requestDto); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/accept")
    public RequestResponseDto Accept(@RequestBody @Valid RequestDto requestDto) { return toDoService.acceptRequest(requestDto); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/deny")
    public boolean Deny(@RequestBody @Valid RequestDto requestDto) { return toDoService.denyRequest(requestDto); }
}
