package ro.unibuc.hello.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ro.unibuc.hello.data.ProductEntity;
import ro.unibuc.hello.data.ProductRepository;
import ro.unibuc.hello.dto.ProductDTO;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {


    @Mock
    ProductRepository mockProductRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        when(mockProductRepository.findByNameContaining("John")).thenReturn(new ArrayList<>(List.of(new ProductEntity(1, "John Doe", 1, "Description", "Category"))));
    }
    @Test
    void test_getProduct_returnsListOfProducts(){
        // Arrange
        String name = "John";

        // Act
        ProductDTO product = productService.findProductsByName(name).get(0);

        // Assert
        Assertions.assertEquals(1, product.getId());
        Assertions.assertEquals("John Doe", product.getName());
    }

    @Test
    void test_getProductByName_returnsNothing(){
        // Arrange

        // Act
        String name = "";

        // Act
        List<ProductDTO> product = productService.findProductsByName(name);

        // Assert
        Assertions.assertEquals(List.of(), product);

    }

}
