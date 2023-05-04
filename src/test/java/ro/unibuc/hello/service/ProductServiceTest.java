package ro.unibuc.hello.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.ProductDTO;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductServiceTest {


    @Mock
    ProductRepository mockProductRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
        when(mockProductRepository.findByNameContaining("John")).thenReturn(new ArrayList<>(List.of(new ProductEntity(1, "John Doe", 1, "Description", "Category"))));
    }

    @Test
    public void test_getProduct_returnsListOfProducts(){
        // Arrange
        String name = "John";

        // Act
        ProductDTO product = productService.findProductsByName(name).get(0);

        // Assert
        Assertions.assertEquals(1, product.getId());
        Assertions.assertEquals("John Doe", product.getName());
    }

    @Test
    public void test_getProductByName_returnsNothing(){
        // Arrange

        // Act
        String name = "";

        // Act
        List<ProductDTO> product = productService.findProductsByName(name);

        // Assert
        Assertions.assertEquals(List.of(), product);

    }

    @Test
    public void test_uploadProduct_uploads_Correctly(){
        // Arrange

        // Act
        String name = "";
        ProductEntity productEntity = new ProductEntity(1, "Product", 1, "Description", "Category");
        ProductDTO myProduct = ProductDTO.transformFromEntity(productEntity);
        // Act
        productService.uploadProduct(myProduct);

        // Assert
        Mockito.verify(mockProductRepository).save(any());

    }


    @Test
    public void test_findAllProducts(){
        // Arrange

        // Act
        List<ProductDTO> products = productService.findAllProducts();

        // Assert
        Mockito.verify(mockProductRepository).findAll();

    }

}
