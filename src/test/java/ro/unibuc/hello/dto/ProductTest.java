package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.dto.Product;

class ProductTest {

    Product test = new Product(1, "Ibanez abc123", "test description", "Guitar");

    @Test
    void test_id(){
        Assertions.assertEquals(1, test.getId());
    }

    @Test
    void test_name(){
        Assertions.assertEquals("Ibanez abc123", test.getName());
    }

    @Test
    void test_description(){
        Assertions.assertEquals("test description", test.getDescription());
    }

    @Test
    void test_categories(){
        Assertions.assertEquals(1, test.getCategories());
    }
}