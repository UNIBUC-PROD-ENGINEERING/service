package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.CategoryDTO;
import ro.unibuc.hello.service.CategoryService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private CategoryController categoryController;


    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    public void test_addCategory() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                        .categoryName("Test Category").build();

        doNothing().when(categoryService).addCategory(categoryDTO);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/categories/addCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO));

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andReturn();

        Assertions.assertTrue(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void test_deleteCategory() throws Exception{
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryName("Test Category").build();

        doNothing().when(categoryService).deleteCategoryById("1");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/categories/deleteCategory/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO));

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andReturn();

        Assertions.assertTrue(result.getResponse().getContentAsString().isEmpty());
}
}