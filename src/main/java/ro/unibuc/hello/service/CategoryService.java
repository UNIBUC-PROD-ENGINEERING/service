package ro.unibuc.hello.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.repository.CategoryRepository;


import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = getCategoryEntityFromDTO(categoryDTO);
        categoryRepository.save(categoryEntity);
    }

    public List<CategoryDTO> getCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryEntities.forEach(categoryEntity -> categoryDTOS.add(getCategoryDTOFromEntity(categoryEntity)));
        return categoryDTOS;
    }

    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO getCategoryDTOFromEntity(CategoryEntity category) {
        return CategoryDTO.builder().categoryName(category.getName()).build();
    }

    private CategoryEntity getCategoryEntityFromDTO(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDTO.getCategoryName());
        return categoryEntity;
    }
}
