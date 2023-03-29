package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.BookDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("IT")
@DisplayName("Book Service Integration Test")
public class BookServiceTestIT {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private Book book1;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();

        book1 = new Book("31", "Mobydick", "Herman", "Adevarul", "cod", LocalDate.now());
        bookRepository.save(book1);
    }

    @Test
    public void testAddBook() {
        BookDTO dto = new BookDTO(null, "Test title", "Test author", "Test publisher", "Test isbn", LocalDate.now());

        BookDTO result = bookService.addBook(dto);

        assertNotNull(result);
        assertEquals(result.getTitle(),dto.getTitle());

        Book savedBook = bookRepository.findById(result.getId()).orElse(null);

        assertNotNull(savedBook);
        assertEquals(savedBook.getTitle(),dto.getTitle());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book("31", "Mobydick", "Herman", "Adevarul", "cod", LocalDate.now());
        book.setTitle("Mobydick");
        book = bookRepository.save(book);

        BookDTO dto = new BookDTO(book.getId(), "Updated bookTitle", book.getAuthor(), book.getPublisher(), book.getIsbn(), book.getPublishedDate());

        BookDTO result = bookService.updateBook(dto);


        assertNotNull(result);
        assertEquals(dto.getTitle(), result.getTitle());

        Book updatedBook = bookRepository.findById(result.getId()).orElse(null);
        assertNotNull(updatedBook);
        assertEquals(dto.getTitle(), updatedBook.getTitle());
    }

    @Test
    public void getBooksTest() {
        List<BookDTO> books = bookService.getBooks();
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Mobydick", books.get(0).getTitle());
    }

    @Test
    public void testDeleteBookById() {
        Book book = new Book("Test bookTitle", "Test author", "Test publisher", "Test isbn", LocalDate.now());
        book.setTitle("Mobydick");
        book = bookRepository.save(book);

        bookService.deleteBookById(book.getId());

        Book deletedBook = bookRepository.findById(book.getId()).orElse(null);
        assertNull(deletedBook);
    }
}