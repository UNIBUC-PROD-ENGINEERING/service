package ro.unibuc.hello.controller;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.GreetingsService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GreetingsControllerTest {

    @Mock
    private GreetingsService greetingsService;

    @InjectMocks
    private GreetingsController greetingsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(greetingsController).build();
    }

    @Test
    void test_sayHello() throws Exception {
        // Arrange
        Greeting greeting = new Greeting("1", "Hello, there!");
        when(greetingsService.hello("there")).thenReturn(greeting);
    
        // Act & Assert
        mockMvc.perform(get("/hello-world?name=there"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.content").value("Hello, there!"));
    }
    

    @Test
    void test_info() throws Exception {
        // Arrange
        Greeting greeting = new Greeting("1", "there : some description");
        when(greetingsService.buildGreetingFromInfo("there")).thenReturn(greeting);
    
        // Act & Assert
        mockMvc.perform(get("/info?title=there"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.content").value("there : some description"));
    }    

    @Test
    void test_info_cascadesException() {
        // Arrange
        String title = "there";
        when(greetingsService.buildGreetingFromInfo(title)).thenThrow(new EntityNotFoundException(title));

        // Act
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> greetingsController.info(title),
                "Expected info() to throw EntityNotFoundException, but it didn't");

        // Assert
        assertTrue(exception.getMessage().contains(title));
    }

    @Test
    void test_getAllGreetings() throws Exception {
        // Arrange
        List<Greeting> greetings = Arrays.asList(new Greeting("1", "Hello"), new Greeting("2", "Hi"));
        when(greetingsService.getAllGreetings()).thenReturn(greetings);

        // Act & Assert
        mockMvc.perform(get("/greetings"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].content").value("Hello"))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[1].content").value("Hi"));
    }

    @Test
    void test_createGreeting() throws Exception {
        // Arrange
        Greeting newGreeting = new Greeting("1", "Hello, World!");
        when(greetingsService.saveGreeting(any(Greeting.class))).thenReturn(newGreeting);
    
        // Act & Assert
        mockMvc.perform(post("/greetings")
               .content("{\"content\":\"Hello, World!\"}")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.content").value("Hello, World!"));
    }
    
    @Test
    void test_updateGreeting() throws Exception {
        // Arrange
        String id = "1";
        Greeting updatedGreeting = new Greeting(id, "Updated Greeting");
        when(greetingsService.updateGreeting(eq(id), any(Greeting.class))).thenReturn(updatedGreeting);
    
        // Act & Assert
        mockMvc.perform(put("/greetings/{id}", id)
               .content("{\"content\":\"Updated Greeting\"}")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value("1"))
               .andExpect(jsonPath("$.content").value("Updated Greeting"));
    }
    
    @Test
    void test_deleteGreeting() throws Exception {
        String id = "1";
        Greeting greeting = new Greeting(id, "Hello");
        when(greetingsService.saveGreeting(any(Greeting.class))).thenReturn(greeting);
    
        // add greeting
        mockMvc.perform(post("/greetings")
               .content("{\"content\":\"Hello\"}")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    
        // delete greeting
        mockMvc.perform(delete("/greetings/{id}", id))
               .andExpect(status().isOk());
    
        verify(greetingsService, times(1)).deleteGreeting(id);
    
        // check if greeting is deleted
        mockMvc.perform(get("/greetings"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isEmpty());
    }
    
}
