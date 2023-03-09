package ro.unibuc.hello.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.service.CategoryService;
import ro.unibuc.hello.service.ProductService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addCategory")
    void addCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder().build();
        categoryService.addCategory(categoryDTO);
    }

    @GetMapping("/getCategories")
    List<CategoryDTO> getAllCategories() {

        return categoryService.getCategories();
    }
}
