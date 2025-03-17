package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;
import ro.unibuc.hello.dto.response.UserListDto;

@RestController
@AllArgsConstructor
@RequestMapping("/item")
public class ItemController {
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/create")
    public Create();

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/create")
    public Update();

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/create")
    public Delete();
}
