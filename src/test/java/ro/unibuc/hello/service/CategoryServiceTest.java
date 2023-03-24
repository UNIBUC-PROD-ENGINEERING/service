package ro.unibuc.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService ;

    private ObjectMapper objectMapper;

    @Test
    void test_addCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryName("Test Category").build();

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDTO.getCategoryName());

        when(categoryRepository.save(any())).thenReturn(categoryEntity);

        categoryService.addCategory(categoryDTO);

        verify(categoryRepository, times(1)).save(any());


    }

    @Test
    void test_getCategories() {
        List<CategoryDTO> expectedResult = new ArrayList<>();
        CategoryDTO categoryDTO = getTestCategoryDTO();
        expectedResult.add(categoryDTO);

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        CategoryEntity categoryEntity = getTestCategoryEntity();
        categoryEntityList.add(categoryEntity);

        when(categoryRepository.findAll()).thenReturn(categoryEntityList);

        List<CategoryDTO> actualResult = categoryService.getCategories();

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void test_deleteCategoryById() {
        doNothing().when(categoryRepository).deleteById(any());

        categoryService.deleteCategoryById("id");

        verify(categoryRepository, times(1)).deleteById(any());
    }

    private CategoryDTO getTestCategoryDTO() {
        return CategoryDTO.builder().categoryName("testCategory").build();
    }

    private CategoryEntity getTestCategoryEntity() {
        return new CategoryEntity("testCategory");
    }
}