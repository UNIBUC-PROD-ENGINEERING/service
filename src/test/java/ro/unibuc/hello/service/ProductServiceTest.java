package ro.unibuc.hello.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void test_getProductById_returnsProductDTO() {
        // Arrange
        String productId = "id";
        ProductEntity productEntity = getTestProductEntity();
        productEntity.setId("id");
        ProductDTO expectedResult = getTestProductDTO();

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        // Act
        ProductDTO actualResult = productService.getProductById(productId);

        // Assert
        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void test_getProductById_throwsEntityNotFoundException() {
        // Arrange
        String productId = "id";

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Assert
        Assertions.assertThatThrownBy(() -> productService.getProductById(productId))
                .isExactlyInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void test_addProduct_expectProductToBeAdded() {
        // Arrange
        ProductDTO productDTO = getTestProductDTO();
        ProductEntity productEntity = getTestProductEntity();
        CategoryEntity categoryEntity = new CategoryEntity("testCategory");

        when(categoryRepository.findByNameEquals("testCategory")).thenReturn(Optional.of(categoryEntity));
        when(productRepository.save(productEntity)).thenReturn(productEntity);

        // Act
        productService.addProduct(productDTO);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void test_addProduct_throwsEntityNotFoundException() {
        // Arrange
        ProductDTO productDTO = getTestProductDTO();
        ProductEntity productEntity = getTestProductEntity();

        when(categoryRepository.findByNameEquals("testCategory")).thenReturn(Optional.empty());
        when(productRepository.save(productEntity)).thenReturn(productEntity);

        Assertions.assertThatThrownBy(() -> productService.addProduct(productDTO))
                .isExactlyInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void test_editProduct_expectProductToBeEdited() {
        // Arrange
        ProductDTO productDTO = getTestProductDTO();
        ProductEntity productEntity = getTestProductEntity();
        CategoryEntity categoryEntity = new CategoryEntity("testCategory");

        when(productRepository.findById(productDTO.getProductId())).thenReturn(Optional.of(productEntity));
        when(categoryRepository.findByNameEquals("testCategory")).thenReturn(Optional.of(categoryEntity));

        // Act
        productService.editProduct(productDTO);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void test_editProduct_givenCategoryNotFound_expectEntityNotFoundExceptionToBeThrown() {
        // Arrange
        ProductDTO productDTO = getTestProductDTO();
        ProductEntity productEntity = getTestProductEntity();

        when(productRepository.findById(productDTO.getProductId())).thenReturn(Optional.of(productEntity));
        when(categoryRepository.findByNameEquals("testCategory")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.editProduct(productDTO))
                .isExactlyInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void test_editProduct_givenProductNotFound_expectEntityNotFoundExceptionToBeThrown() {
        // Arrange
        ProductDTO productDTO = getTestProductDTO();

        when(productRepository.findById(productDTO.getProductId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> productService.editProduct(productDTO))
                .isExactlyInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void test_getProductsByNameContains_returnsProductDTOList() {
        // Arrange
        List<ProductDTO> expectedResult = new ArrayList<>();
        ProductDTO productDTO = getTestProductDTO();
        expectedResult.add(productDTO);

        List<ProductEntity> productEntityList = new ArrayList<>();
        ProductEntity productEntity = getTestProductEntity();
        productEntity.setId(productDTO.getProductId());
        productEntityList.add(productEntity);

        when(productRepository.findByProductNameContainsIgnoreCase(any())).thenReturn(productEntityList);

        List<ProductDTO> actualResult = productService.getProductsByNameContains("name");

        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void test_getProductsByCategory_returnsProductDTOList() {
        // Arrange
        List<ProductDTO> expectedResult = new ArrayList<>();
        ProductDTO productDTO = getTestProductDTO();
        expectedResult.add(productDTO);

        List<ProductEntity> productEntityList = new ArrayList<>();
        ProductEntity productEntity = getTestProductEntity();
        productEntity.setId(productDTO.getProductId());
        productEntityList.add(productEntity);
        ProductEntity productEntityWithoutMatchingCategoryName = new ProductEntity();
        productEntityWithoutMatchingCategoryName.setCategory(new CategoryEntity("hehe"));

        when(productRepository.findAll()).thenReturn(productEntityList);

        List<ProductDTO> actualResult = productService.getProductsByCategory(productEntity.getCategory().getName());

        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void test_getProductsByPriceBetween_returnsProductDTOList() {
        // Arrange
        Float lowerBoundPrice = 50f;
        Float upperBoundPrice = 12000f;
        List<ProductDTO> expectedResult = new ArrayList<>();
        ProductDTO productDTO = getTestProductDTO();
        expectedResult.add(productDTO);

        List<ProductEntity> productEntityList = new ArrayList<>();
        ProductEntity productEntity = getTestProductEntity();
        productEntity.setId(productDTO.getProductId());
        productEntityList.add(productEntity);

        when(productRepository.findByPriceBetween(lowerBoundPrice, upperBoundPrice)).thenReturn(productEntityList);

        List<ProductDTO> actualResult = productService.getProductsByPriceBetween(lowerBoundPrice, upperBoundPrice);

        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void test_getProducts_returnsProductDTOList() {
        // Arrange
        List<ProductDTO> expectedResult = new ArrayList<>();
        ProductDTO productDTO = getTestProductDTO();
        expectedResult.add(productDTO);

        List<ProductEntity> productEntityList = new ArrayList<>();
        ProductEntity productEntity = getTestProductEntity();
        productEntity.setId(productDTO.getProductId());
        productEntityList.add(productEntity);

        when(productRepository.findAll()).thenReturn(productEntityList);

        List<ProductDTO> actualResult = productService.getProducts();

        Assertions.assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void test_deleteProductById_expectProductToBeDeleted() {
        // Arrange
        ProductDTO productDTO = getTestProductDTO();

        doNothing().when(productRepository).deleteById(any());

        // Act
        productService.deleteProductById(productDTO.getProductId());

        verify(productRepository, times(1)).deleteById(productDTO.getProductId());
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

    private ProductEntity getTestProductEntity() {
        CategoryEntity testCategoryEntity = new CategoryEntity("testCategory");
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("testName");
        productEntity.setBrandName("testBrand");
        productEntity.setProductDescription("testDescription");
        productEntity.setPrice(70f);
        productEntity.setStock(20L);
        productEntity.setCategory(testCategoryEntity);
        return productEntity;
    }
}
