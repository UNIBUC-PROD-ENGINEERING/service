package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.auth.PublicEndpoint;
import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.ItemCreateRequest;
import ro.unibuc.hello.dto.ItemUpdateRequest;
import ro.unibuc.hello.service.ItemsService;

@Controller
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @PublicEndpoint
    @GetMapping("/items")
    @ResponseBody
    public List<Item> getAllItems() {
        return itemsService.getAllItems();
    }

    @PostMapping("/items")
    @ResponseBody
    public Item createItem(@RequestBody ItemCreateRequest item) {
        return itemsService.saveItem(item);
    }

    @PublicEndpoint
    @GetMapping("/items/{id}")
    @ResponseBody
    public Item getItemById(@PathVariable String id) {
        return itemsService.getItemById(id);
    }

    @PutMapping("/items/{id}")
    @ResponseBody
    public Item updateItem(@PathVariable String id, @RequestBody ItemUpdateRequest item) {
        return itemsService.updateItem(id, item);
    }


    @DeleteMapping("/items/{id}")
    @ResponseBody
    public void deleteItem(@PathVariable String id) {
          itemsService.deleteItem(id);
    }
}
