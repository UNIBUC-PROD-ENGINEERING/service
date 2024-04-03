package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ro.unibuc.hello.data.BookEntity;

class BookEntityTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        BookEntity book = new BookEntity();
        
        // Act
        book.setId("1");
        book.setTitle("Title");
        book.setAuthor("Author");

        // Assert
        Assertions.assertEquals("1", book.getId());
        Assertions.assertEquals("Title", book.getTitle());
        Assertions.assertEquals("Author", book.getAuthor());
    }

    @Test
    void testToString() {
        // Arrange
        BookEntity book = new BookEntity("Title", "Author");
        book.setId("1");

        // Act
        String expectedString = "BookEntity{id='1', title='Title', author='Author'}";

        // Assert
        Assertions.assertEquals(expectedString, book.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        BookEntity book1 = new BookEntity("Title", "Author");
        book1.setId("1");

        BookEntity book2 = new BookEntity("Title", "Author");
        book2.setId("1");

        BookEntity book3 = new BookEntity("Another Title", "Another Author");
        book3.setId("2");

        // Assert
        Assertions.assertEquals(book1, book2);
        Assertions.assertNotEquals(book1, book3);
        Assertions.assertEquals(book1.hashCode(), book2.hashCode());
        Assertions.assertNotEquals(book1.hashCode(), book3.hashCode());
    }
}
