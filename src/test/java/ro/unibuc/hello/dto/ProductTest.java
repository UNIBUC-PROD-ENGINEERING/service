package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.data.ProductEntity;

public class ProductTest {

    ProductEntity productEntity = new ProductEntity(1, "Product", 1, "Description", "Category");
    ProductDTO myProduct = ProductDTO.transformFromEntity(productEntity);

    @Test
    void test_id(){
        Assertions.assertSame(1, myProduct.getId());
    }
    @Test
    void test_name(){
        Assertions.assertEquals("Product", myProduct.getName());
    }
   @Test
    void test_quantity(){
        Assertions.assertEquals(1, myProduct.getQuantity());
    }

    @Test
    void test_description(){
        Assertions.assertEquals("Description", myProduct.getDescription());
    }

    @Test
    void test_category(){
        Assertions.assertEquals("Category", myProduct.getCategory());
    }


}
