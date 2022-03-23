package ro.unibuc.hello.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.dto.Product;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    public void dataSetup(){
        Product product = new Product("Yeezy 500","Tan",350);

        product.setProductId("1kl3j1j2lk3");

        productRepository.save(product);
    }



    @Test
    void findProductById() {
        Product product = new Product("Yeezy 500","Tan",350);

        product.setProductId("1kl3j1j2lk3");

        Product result = productRepository.findProductById("1kl3j1j2lk3");

        assertEquals(result.getModelName(), product.getModelName());
        assertEquals(result.getColorway(), product.getColorway());
        assertEquals(result.getRetailPrice(), product.getRetailPrice());
    }
}