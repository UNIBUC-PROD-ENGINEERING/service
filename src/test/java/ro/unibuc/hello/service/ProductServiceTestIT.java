package ro.unibuc.hello.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.entity.CategoryEntity;
import ro.unibuc.hello.entity.ProductEntity;
import ro.unibuc.hello.repository.CategoryRepository;
import ro.unibuc.hello.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        CategoryEntity testCategoryEntity = new CategoryEntity();
        testCategoryEntity.setName(testCategoryDTO.getCategoryName());
        ProductDTO productDto = ProductDTO.builder()
                .productId("id")
                .productName("testName")
                .brandName("testBrand")
                .productDescription("testDescription")
                .price(70f)
                .stock(20L)
                .category(testCategoryDTO)
                .build();

        String categoryName = productDto.getCategory().getCategoryName();
        categoryRepository.save(testCategoryEntity);
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByNameEquals(categoryName);

        productService.addProduct(productDto);
        Optional<ProductEntity> productOptional = productRepository.findById(productDto.getProductId());

        assertEquals("id", productDto.getProductId());
        assertEquals("testName",productDto.getProductName());
        categoryRepository.deleteById(categoryOptional.get().getId());
        productRepository.deleteById(productOptional.get().getId());

    }

    @Test
    void test_getProductsByCategory_returnsProductDTOList() {
        CategoryEntity categoryToGetProductsBy = new CategoryEntity("itTestGoodCategory");
        CategoryEntity otherCategory = new CategoryEntity("itTestBadCategory");

        List<CategoryEntity> categoriesToBeAddedToTheDb = new ArrayList<>();
        categoriesToBeAddedToTheDb.add(categoryToGetProductsBy);
        categoriesToBeAddedToTheDb.add(otherCategory);

        categoryRepository.saveAll(categoriesToBeAddedToTheDb);

        List<ProductEntity> productsToBeAddedToTheDb = new ArrayList<>();
        productsToBeAddedToTheDb.add(
                new ProductEntity("id1", "name1", "desc1", 2.0f, "brand1", 3L, categoryToGetProductsBy)
        );
        productsToBeAddedToTheDb.add(
                new ProductEntity("id2", "name2", "desc2", 3.0f, "brand2", 4L, categoryToGetProductsBy)
        );
        productsToBeAddedToTheDb.add(
                new ProductEntity("id3", "name3", "desc3", 4.0f, "brand3", 5L, otherCategory)
        );

        productRepository.saveAll(productsToBeAddedToTheDb);

        List<ProductDTO> expectedResult = new ArrayList<>();
        expectedResult.add(getProductDTOFromEntity(productsToBeAddedToTheDb.get(0)));
        expectedResult.add(getProductDTOFromEntity(productsToBeAddedToTheDb.get(1)));

        List<ProductDTO> actualResult = productService.getProductsByCategory(categoryToGetProductsBy.getName());

        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

        // delete the newly added test categories and products
        categoryRepository.deleteAll(categoriesToBeAddedToTheDb);
        productRepository.deleteAll(productsToBeAddedToTheDb);
    }

    @Test
    void test_deleteProductById_expectProductToBeDeleted() {
        CategoryEntity categoryEntity = new CategoryEntity("itTestCategory");
        categoryRepository.save(categoryEntity);

        ProductEntity productEntity = new ProductEntity("id1", "name1", "desc1", 2.0f, "brand1", 3L, categoryEntity);
        productRepository.save(productEntity);

        // Act
        productService.deleteProductById(productEntity.getId());

        Optional<ProductEntity> productEntityOptional = productRepository.findById(productEntity.getId());

        assertTrue(productEntityOptional.isEmpty());

        categoryRepository.delete(categoryEntity);
    }

    private ProductDTO getProductDTOFromEntity(ProductEntity product) {
        return ProductDTO.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .brandName(product.getBrandName())
                .price(product.getPrice())
                .category(CategoryDTO.builder().categoryName(product.getCategory().getName()).build())
                .stock(product.getStock()).build();
    }

}