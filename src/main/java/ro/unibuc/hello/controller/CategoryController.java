package ro.unibuc.hello.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.service.CategoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addCategory")
    public void addCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);
    }

    @GetMapping("/getCategories")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getCategories();
    }
}
