package ro.unibuc.hello.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllBooks() throws Exception {
        // Arrange
        BookEntity book1 = new BookEntity("Title 1", "Author 1");
        BookEntity book2 = new BookEntity("Title 2", "Author 2");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act & Assert
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookById() throws Exception {
        // Arrange
        String id = "1";
        BookEntity book = new BookEntity("Title", "Author");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act & Assert
        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateBook() throws Exception {
        // Arrange
        BookEntity book = new BookEntity("Title", "Author");
        when(bookRepository.save(book)).thenReturn(book);

        // Act & Assert
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBook() throws Exception {
        // Arrange
        String id = "1";

        // Act & Assert
        mockMvc.perform(delete("/books/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testEditBook() throws Exception {
        // Arrange
        String id = "1";
        BookEntity book = new BookEntity("Updated Title", "Updated Author");
        when(bookRepository.findById(id)).thenReturn(Optional.of(new BookEntity("Old Title", "Old Author")));

        // Act & Assert
        mockMvc.perform(put("/books/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }
}
