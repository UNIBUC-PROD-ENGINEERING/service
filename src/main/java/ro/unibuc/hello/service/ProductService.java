package ro.unibuc.hello.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.entity.ProductEntity;
import ro.unibuc.hello.repository.ProductRepository;

@Component
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(ProductDTO productDTO) {
        ProductEntity productEntity = getMockProduct();
        productRepository.save(productEntity);
    }

    private ProductEntity getMockProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("Telefon Smasone 2300");
        productEntity.setId("mama");
        productEntity.setProductDescription("cel mai bun tlf face de mancare");
        productEntity.setPrice(10000.0F);
        productEntity.setBrandName("SAMSONE");
        productEntity.setStock(20L);
        productEntity.setCategory(new CategoryEntity("miau", "nume"));
        return productEntity;
    }

}
