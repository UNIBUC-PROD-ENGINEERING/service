package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.dto.BookCreationRequestDto;
import ro.unibuc.hello.service.BookService;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    @ResponseBody
    public ResponseEntity<BookEntity> createBook(@RequestBody BookCreationRequestDto bookCreationRequestDto) {
        var newBook = bookService.saveBook(bookCreationRequestDto);
        return ResponseEntity.ok(newBook);
    }

    @GetMapping("/books/authors/{authorId}")
    @ResponseBody
    public ResponseEntity<List<BookEntity>> getBooksByAuthor(@PathVariable String authorId) {
        var books = bookService.getBooksByAuthor(authorId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/readers/{readerId}")
    @ResponseBody
    public ResponseEntity<List<BookEntity>> getBooksByReader(@PathVariable String readerId) {
        var books = bookService.getBooksByReader(readerId);
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable String bookId) {
        bookService.deleteBookAndReadingRecords(bookId);
        return ResponseEntity.ok("Book and associated records deleted successfully");
    }

}
