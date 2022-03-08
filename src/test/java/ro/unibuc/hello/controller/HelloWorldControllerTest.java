package ro.unibuc.hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.HelloWorldService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HelloWorldControllerTest {

    @Mock
    private HelloWorldService helloWorldService;

    @InjectMocks
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_sayHello() throws Exception {
        // Arrange
        Greeting greeting = new Greeting(1, "Hello, there!");

        when(helloWorldService.hello(any())).thenReturn(greeting);

        // Act
        MvcResult result = mockMvc.perform(get("/hello-world?name=there")
                .content(objectMapper.writeValueAsString(greeting))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(greeting));
    }

    @Test
    void test_info() throws Exception {
        // Arrange
        Greeting greeting = new Greeting(1, "there : some description");

        when(helloWorldService.buildGreetingFromInfo(any())).thenReturn(greeting);

        // Act
        MvcResult result = mockMvc.perform(get("/info?title=there")
                .content(objectMapper.writeValueAsString(greeting))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(greeting));
    }

    @Test
    void test_info_cascadesException() {
        // Arrange
        String title = "there";
        when(helloWorldService.buildGreetingFromInfo(any())).thenThrow(new EntityNotFoundException(title));

        // Act
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> helloWorldController.info(title),
                "Expected info() to throw EntityNotFoundException, but it didn't");

        // Assert
        assertTrue(exception.getMessage().contains(title));
    }
}