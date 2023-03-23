package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.dto.ProductDTO;
import ro.unibuc.hello.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_addProduct() throws Exception {
        // Arrange
        ProductDTO testProductDTO = getTestProductDTO();

        doNothing().when(productService).addProduct(testProductDTO);

        // Act
        MvcResult result = mockMvc.perform(post("/products/addProduct")
                        .content(objectMapper.writeValueAsString(testProductDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Assertions.assertTrue(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void test_editProduct() throws Exception {
        // Arrange
        ProductDTO testProductDTO = getTestProductDTO();

        doNothing().when(productService).addProduct(testProductDTO);

        // Act
        MvcResult result = mockMvc.perform(put("/products/editProduct")
                        .content(objectMapper.writeValueAsString(testProductDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Assertions.assertTrue(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void test_getProducts() throws Exception {
        // Arrange
        List<ProductDTO> testProductDTOList = new ArrayList<>();
        ProductDTO testProductDTO = getTestProductDTO();
        testProductDTOList.add(testProductDTO);

        when(productService.getProducts()).thenReturn(testProductDTOList);

        // Act
        MvcResult result = mockMvc.perform(get("/products/getProducts")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(testProductDTOList));
    }

    @Test
    void test_getProductById() throws Exception {
        // Arrange
        ProductDTO testProductDTO = getTestProductDTO();
        String id = testProductDTO.getProductId();

        when(productService.getProductById(id)).thenReturn(testProductDTO);

        // Act
        MvcResult result = mockMvc.perform(get("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(testProductDTO));
    }

    @Test
    void test_filterByNameContains() throws Exception {
        // Arrange
        List<ProductDTO> testProductDTOList = new ArrayList<>();
        ProductDTO testProductDTO = getTestProductDTO();
        testProductDTOList.add(testProductDTO);
        String name = testProductDTO.getProductName();

        when(productService.getProductsByNameContains(name)).thenReturn(testProductDTOList);

        // Act
        MvcResult result = mockMvc.perform(get("/products/filterByNameContains?name=" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(testProductDTOList));
    }

    @Test
    void test_filterByCategory() throws Exception {
        // Arrange
        List<ProductDTO> testProductDTOList = new ArrayList<>();
        ProductDTO testProductDTO = getTestProductDTO();
        testProductDTOList.add(testProductDTO);
        String categoryName = "testCategory";

        when(productService.getProductsByCategory(categoryName)).thenReturn(testProductDTOList);

        // Act
        MvcResult result = mockMvc.perform(get("/products/filterByCategory?categoryName=" + categoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(testProductDTOList));
    }

    @Test
    void test_filterByPriceBetween() throws Exception {
        // Arrange
        List<ProductDTO> testProductDTOList = new ArrayList<>();
        ProductDTO testProductDTO = getTestProductDTO();
        testProductDTOList.add(testProductDTO);
        Float lowerBoundPrice = 100f;
        Float upperBoundPrice = 2000f;

        when(productService.getProductsByPriceBetween(lowerBoundPrice, upperBoundPrice))
                .thenReturn(testProductDTOList);

        // Act
        MvcResult result = mockMvc.perform(get("/products/filterByPriceBetween?lowerBoundPrice=" + lowerBoundPrice
                            + "&upperBoundPrice=" + upperBoundPrice)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(testProductDTOList));
    }

    @Test
    void test_deleteProductById() throws Exception {
        // Arrange
        ProductDTO testProductDTO = getTestProductDTO();

        doNothing().when(productService).deleteProductById(testProductDTO.getProductId());

        // Act
        MvcResult result = mockMvc.perform(delete("/products/deleteProduct/" + testProductDTO.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Assert
        Assertions.assertTrue(result.getResponse().getContentAsString().isEmpty());
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
}
