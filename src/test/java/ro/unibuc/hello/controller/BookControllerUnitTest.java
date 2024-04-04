package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import ro.unibuc.hello.data.BookEntity;

import java.awt.print.Book;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerUnitTest {
    BookController bookController = new BookController();
    @Test
    void newBookCreatedSuccesfully() {
        //Arrange
        BookEntity book = new BookEntity("Carte de bucate", "Alin Bucataru");

        //Act
        BookEntity savedBook = bookController.createBook(book);

        //Assert
        Assertions.assertNotNull(savedBook);
        Assertions.assertTrue(Integer.parseInt(savedBook.getId()) > 0);
    }

    @Test
    void bookReadByIdSuccessfully() {
        //Arrange
        List <BookEntity> bookList = bookController.getAllBooks();
        BookEntity book = bookList.get(0);

        //Act
        BookEntity readBook = bookController.getBookById(book.getId());

        //Assert
        Assertions.assertNotNull(readBook);
    }

    @Test
    void deleteBookSuccesfully() {
        // Arrange
        BookEntity book = new BookEntity("Test Book", "Test Author");
        BookEntity savedBook = bookController.createBook(book);

        // Act
        bookController.deleteBook(savedBook.getId());

        // Assert
        Assertions.assertThrows(InvalidConfigurationPropertyValueException.class, () -> {
            bookController.getBookById(savedBook.getId());
        });
    }

    @Test
    void editBookSuccessfully() {
        // Arrange
        // Create a new book
        BookEntity book = new BookEntity("Om bogat Om sarac", "Irwin Shaw");
        BookEntity savedBook = bookController.createBook(book);

        // Update details for the book
        String updatedTitle = "Tata Bogat Tata Sarac";
        String updatedAuthor = "Robert Kyosaki";
        savedBook.setTitle(updatedTitle);
        savedBook.setAuthor(updatedAuthor);

        // Act
        // Call the updateBook method to edit the book details
        bookController.editBooks(savedBook.getId(), savedBook);
        BookEntity editedBook = bookController.getBookById(savedBook.getId());

        // Assert
        // Check if the book details have been updated correctly
        Assertions.assertNotNull(editedBook);
        Assertions.assertEquals(savedBook.getId(), editedBook.getId());
        Assertions.assertEquals(updatedTitle, editedBook.getTitle());
        Assertions.assertEquals(updatedAuthor, editedBook.getAuthor());
    }
}