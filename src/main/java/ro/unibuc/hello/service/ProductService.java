package ro.unibuc.hello.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.entity.ProductEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.repository.CategoryRepository;
import ro.unibuc.hello.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public void addProduct(ProductDTO productDTO) throws EntityNotFoundException{
        String productName = productDTO.getCategory().getCategoryName();
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByNameEquals(productName);
        if (categoryOptional.isEmpty()) {
            throw new EntityNotFoundException(productName);
        } else {
            CategoryEntity categoryEntity = categoryOptional.get();
            ProductEntity productEntity = getProductEntityFromDTO(productDTO);
            productEntity.setCategory(categoryEntity);
            productRepository.save(productEntity);
        }
    }

    public ProductDTO getProductById(String productId) throws EntityNotFoundException {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        if (productEntityOptional.isEmpty()) {
            throw new EntityNotFoundException(productId);
        } else {
            ProductEntity productEntity = productEntityOptional.get();
            return getProductDTOFromEntity(productEntity);
        }
    }

    public List<ProductDTO> getProductsByNameContains(String name) {
        List<ProductEntity> productEntities = productRepository.findByProductNameContainsIgnoreCase(name);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productEntities.forEach(product -> productDTOS.add(getProductDTOFromEntity(product)));
        return productDTOS;
    }

    public List<ProductDTO> getProductsByCategory(String categoryName) {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        productEntities.forEach(productEntity -> {
            if (productEntity.getCategory().getName().equals(categoryName)) {
                productDTOS.add(getProductDTOFromEntity(productEntity));
            }
        });
        return productDTOS;
    }

    public List<ProductDTO> getProductsByPriceBetween(Float lowerBoundPrice, Float upperBoundPrice) {
        List<ProductEntity> productEntities =
                productRepository.findByPriceBetween(lowerBoundPrice, upperBoundPrice);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productEntities.forEach(product -> productDTOS.add(getProductDTOFromEntity(product)));
        return productDTOS;
    }

    public List<ProductDTO> getProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        productEntities.forEach(product -> productDTOS.add(getProductDTOFromEntity(product)));
        return productDTOS;
    }

    private ProductDTO getProductDTOFromEntity(ProductEntity product) {
        return ProductDTO.builder().productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .brandName(product.getBrandName())
                .price(product.getPrice())
                .category(CategoryDTO.builder().categoryName(product.getCategory().getName()).build())
                .stock(product.getStock()).build();
    }

    private ProductEntity getProductEntityFromDTO(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductDescription(productDTO.getProductDescription());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setBrandName(productDTO.getBrandName());
        productEntity.setStock(productDTO.getStock());
        productEntity.setCategory(new CategoryEntity(productDTO.getCategory().getCategoryName()));
        return productEntity;
    }

}
