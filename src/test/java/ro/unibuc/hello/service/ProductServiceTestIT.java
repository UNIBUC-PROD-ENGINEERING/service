package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.entity.ProductEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.repository.CategoryRepository;
import ro.unibuc.hello.repository.InformationRepository;
import ro.unibuc.hello.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@Tag("IT")
class ProductServiceTestIT {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;


    @Test
    void test_addProduct_expectProductToBeAdded() {
        // given

        CategoryDTO testCategoryDTO = CategoryDTO.builder().categoryName("testCategory").build();
        CategoryEntity testCategoryEntity= new CategoryEntity();
        testCategoryEntity.setName(testCategoryDTO.getCategoryName());
        ProductDTO productDto= ProductDTO.builder()
                .productId("id")
                .productName("testName")
                .brandName("testBrand")
                .productDescription("testDescription")
                .price(70f)
                .stock(20L)
                .category(testCategoryDTO)
                .build();

        String categoryName= productDto.getCategory().getCategoryName();
        categoryRepository.save(testCategoryEntity);
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByNameEquals(categoryName);

        productService.addProduct(productDto);
        Optional<ProductEntity> productOptional = productRepository.findById(productDto.getProductId());

        assertEquals("id", productDto.getProductId());
        assertEquals("testName",productDto.getProductName());
        categoryRepository.deleteById(categoryOptional.get().getId());
        productRepository.deleteById(productOptional.get().getId());

    }
    private ProductDTO getTestProductDTO() {
        CategoryDTO testCategoryDTO = CategoryDTO.builder().categoryName("testCategory").build();

        return ProductDTO.builder()
                .productId("id")
                .productName("testName")
                .brandName("testBrand")
                .productDescription("testDescription")
                .price(70f)
                .stock(20L)
                .category(testCategoryDTO)
                .build();
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