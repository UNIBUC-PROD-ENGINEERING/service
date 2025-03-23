package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.dto.Item;
import ro.unibuc.hello.dto.ItemPost;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ItemsService;

import java.util.List;

@Controller
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @GetMapping("/items")
    @ResponseBody
    public List<Item> getAllItems() {
        return itemsService.getAllItems();
    }

    @PostMapping("/items")
    @ResponseBody
    public Item createItem(@RequestBody ItemPost item) {
        return itemsService.saveItem(item);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
          
        Item item = itemsService.getItemById(id);

        if (item != null) {
          return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/items/{id}")
    @ResponseBody
    public Item updateItem(@PathVariable String id, @RequestBody Item item) throws EntityNotFoundException {
        return itemsService.updateItem(id, item);
    }


    @DeleteMapping("/items/{id}")
    @ResponseBody
    public void deleteItem(@PathVariable String id) throws EntityNotFoundException {
          itemsService.deleteItem(id);
    }

}

