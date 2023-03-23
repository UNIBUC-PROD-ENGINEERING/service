package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDTOTest {

    CategoryDTO myCategory = new CategoryDTO("Test the name of the Category");
    CategoryDTO emptyCategory = CategoryDTO.builder().build();
    CategoryDTO noArgsCategory = new CategoryDTO();

    @Test
    void test_name(){
        Assertions.assertSame("Test the name of the Category", myCategory.getCategoryName());
    }
    @Test
    void test_nullCategoryName(){
        Assertions.assertNull( emptyCategory.getCategoryName());
    }

    @Test
    void test_setCategoryName(){
        noArgsCategory.setCategoryName("Jucarele");
        Assertions.assertSame("Jucarele", noArgsCategory.getCategoryName());

    }

}