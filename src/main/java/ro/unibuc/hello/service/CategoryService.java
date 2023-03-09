package ro.unibuc.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.entity.ProductEntity;
import ro.unibuc.hello.repository.CategoryRepository;


import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = getMockCategory();
        categoryRepository.save(categoryEntity);
    }

    private CategoryEntity getMockCategory() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Tilifoane");
        return categoryEntity;
    }
    public List<CategoryDTO> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryDTO> categoriesDto = new ArrayList<>();
        categories.forEach(category -> {
            categoriesDto.add(CategoryDTO.builder().categoryName(category.getName()).build());
        });
        return categoriesDto;
    }
}
