package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

}
