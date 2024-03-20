package ro.unibuc.hello.controllers;

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
import ro.unibuc.hello.dtos.GreetingDTO;
import ro.unibuc.hello.exceptions.EntityNotFoundException;
import ro.unibuc.hello.services.HelloWorldService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
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
        GreetingDTO greetingDTO = new GreetingDTO(1, "Hello, there!");

        when(helloWorldService.hello("there")).thenReturn(greetingDTO);

        // Act
        MvcResult result = mockMvc.perform(get("/hello-world?name=there")
                .content(objectMapper.writeValueAsString(greetingDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(greetingDTO), result.getResponse().getContentAsString());
    }

    @Test
    void test_info() throws Exception {
        // Arrange
        GreetingDTO greetingDTO = new GreetingDTO(1, "there : some description");

        when(helloWorldService.buildGreetingFromInfo("there")).thenReturn(greetingDTO);

        // Act
        MvcResult result = mockMvc.perform(get("/info?title=there")
                .content(objectMapper.writeValueAsString(greetingDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(greetingDTO), result.getResponse().getContentAsString());
    }

    @Test
    void test_info_cascadesException() {
        // Arrange
        String title = "there";
        when(helloWorldService.buildGreetingFromInfo(title)).thenThrow(new EntityNotFoundException(title));

        // Act
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> helloWorldController.info(title),
                "Expected info() to throw EntityNotFoundException, but it didn't");

        // Assert
        assertTrue(exception.getMessage().contains(title));
    }
}
