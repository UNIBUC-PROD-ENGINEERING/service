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
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private final ToDoService toDoService;

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/create")
    public boolean Create(ItemDto itemDto) { return toDoService.createItem(itemDto); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/update")
    public boolean Update(ItemDto itemDto) { return toDoService.updateItem(itemDto); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/delete")
    public boolean Delete(ItemDto itemDto) { return toDoService.deleteItem(itemDto); }
}
