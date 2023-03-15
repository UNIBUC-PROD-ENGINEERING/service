package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.ClothingItem;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ClothingItemService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ClothesController {
    @Autowired
    private ClothingItemService clothingItemService;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEntityNotFoundException(
            EntityNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @PostMapping("/clothes")
    public void insert(@RequestBody ClothingItem clothingItem, HttpServletResponse response) {
        clothingItemService.insert(clothingItem);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("/clothes")
    @ResponseBody
    public List<ClothingItem> getClothes(){
        return clothingItemService.getClothingItems();
    }

    @GetMapping("/clothes/{clothingItemId}")
    @ResponseBody
    public ClothingItem getClothingItem(@PathVariable String clothingItemId){
        return clothingItemService.getClothingItemById(clothingItemId);
    }

    @DeleteMapping("/clothes/{clothingItemId}")
    public String delete(@PathVariable String clothingItemId) {
        return  this.clothingItemService.deleteItem(clothingItemId);
    }
}
