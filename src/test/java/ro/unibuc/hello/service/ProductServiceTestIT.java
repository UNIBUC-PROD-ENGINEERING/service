package ro.unibuc.hello.service;

import org.assertj.core.api.Assertions;
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

    @MockBean
    ProductRepository productRepository;
    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @Test
    void test_addProduct_expectProductToBeAdded() {
        // given
        ProductDTO productDTO = getTestProductDTO();
        ProductEntity productEntity = getProductEntityFromDTO(productDTO);
        CategoryEntity categoryEntity = new CategoryEntity("testCategory");
        given(categoryRepository.findByNameEquals("testCategory")).willReturn(Optional.of(categoryEntity));
        given(productRepository.save(productEntity)).willReturn(productEntity);

        //when
        productService.addProduct(productDTO);

        // then
        ArgumentCaptor<ProductEntity> productEntityArgumentCaptor=
                ArgumentCaptor.forClass(ProductEntity.class);
        verify(productRepository).
               save(productEntityArgumentCaptor.capture());
        ProductEntity captureProduct=productEntityArgumentCaptor.getValue();
        assertEquals(captureProduct.getBrandName(),productEntity.getBrandName());
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