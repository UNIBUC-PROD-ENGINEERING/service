package ro.unibuc.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.repository.CategoryRepository;
import ro.unibuc.hello.repository.InformationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    CategoryService categoryService ;

    private ObjectMapper objectMapper;
    @Test
    void test_addCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryName("Test Category").build();

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDTO.getCategoryName());

        categoryService.addCategory(categoryDTO);

        verify(categoryService, times(1)).addCategory(categoryDTO);
        
       
    }

    @Test
    void test_getCategories() {
    }

    @Test
    void test_deleteCategoryById() {
    }
}