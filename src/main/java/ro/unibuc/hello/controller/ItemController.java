package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ro.unibuc.hello.dto.request.ItemDto;
import ro.unibuc.hello.dto.response.ItemResponseDto;
import ro.unibuc.hello.service.*;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private final ToDoService toDoService;

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/create")
    public ItemResponseDto Create(@RequestBody @Valid ItemDto itemDto) { return toDoService.createItem(itemDto); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PutMapping("/update/{itemName}")
    public ItemResponseDto Update(@RequestBody @Valid ItemDto itemDto, @PathVariable String itemName) { return toDoService.updateItem(itemDto,itemName); }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @DeleteMapping("/delete")
    public boolean Delete(@RequestBody ItemDto itemDto) { return toDoService.deleteItem(itemDto); }
}
