package ro.unibuc.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.entity.ProductEntity;
import ro.unibuc.hello.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(ProductDTO productDTO) {
        ProductEntity productEntity = getMockProduct();
        productRepository.save(productEntity);
    }

    public List<ProductDTO> getProducts() {
        List<ProductEntity> products = productRepository.findAll();
        List<ProductDTO> productsDto = new ArrayList<>();
        products.forEach(product -> {
            productsDto.add(ProductDTO.builder().productName(product.getProductName())
                    .productDescription(product.getProductDescription())
                    .brandName(product.getBrandName())
                    .price(product.getPrice())
                    //.categoryDTO(CategoryDTO.builder().categoryName(product.getCategory().getName()).build())
                    .stock(product.getStock()).build());

        });
        System.out.println(productsDto);
        return productsDto;
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
