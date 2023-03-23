package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductDTOTest {

    CategoryDTO testCategoryDTO = CategoryDTO.builder().categoryName("testCategory").build();

    ProductDTO testProductDTO = ProductDTO.builder()
            .productId("id")
            .productName("testName")
            .brandName("testBrand")
            .productDescription("testDescription")
            .price(70f)
            .stock(20L)
            .category(testCategoryDTO)
            .build();

    ProductDTO emptyTestProductDTO = new ProductDTO();

    @Test
    void test_getId() {
        Assertions.assertSame("id", testProductDTO.getProductId());
    }

    @Test
    void test_productName() {
        Assertions.assertSame("testName", testProductDTO.getProductName());
    }

    @Test
    void test_brandName() {
        Assertions.assertSame("testBrand", testProductDTO.getBrandName());
    }

    @Test
    void test_productDescription() {
        Assertions.assertSame("testDescription", testProductDTO.getProductDescription());
    }

    @Test
    void test_price() {
        Assertions.assertEquals(70f, testProductDTO.getPrice());
    }

    @Test
    void test_stock() {
        Assertions.assertSame(20L, testProductDTO.getStock());
    }

    @Test
    void test_categoryName() {
        Assertions.assertSame("testCategory", testProductDTO.getCategory().getCategoryName());
    }

    @Test
    void test_setId() {
        emptyTestProductDTO.setProductId("id");
        Assertions.assertSame("id", testProductDTO.getProductId());
    }

    @Test
    void test_setProductName() {
        emptyTestProductDTO.setProductName("testName");
        Assertions.assertSame("testName", testProductDTO.getProductName());
    }

    @Test
    void test_setBrandName() {
        emptyTestProductDTO.setBrandName("testBrand");
        Assertions.assertSame("testBrand", testProductDTO.getBrandName());
    }

    @Test
    void test_setProductDescription() {
        emptyTestProductDTO.setProductDescription("testDescription");
        Assertions.assertSame("testDescription", testProductDTO.getProductDescription());
    }

    @Test
    void test_setPrice() {
        emptyTestProductDTO.setPrice(70f);
        Assertions.assertEquals(70f, testProductDTO.getPrice());
    }

    @Test
    void test_setStock() {
        emptyTestProductDTO.setStock(20L);
        Assertions.assertSame(20L, testProductDTO.getStock());
    }

    @Test
    void test_setCategoryService() {
        emptyTestProductDTO.setCategory(testCategoryDTO);
        Assertions.assertSame(testCategoryDTO, testProductDTO.getCategory());
    }
}
