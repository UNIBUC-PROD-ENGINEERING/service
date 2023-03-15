package com.bookstore.v1.controllers;

import com.bookstore.v1.dto.BookDTO;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add-book")
    @ResponseBody
    public BookDTO addBook(@RequestBody BookDTO bookDTO) throws EmptyFieldException {
        return bookService.addBook(bookDTO);
    }

    @PutMapping("/update-book")
    @ResponseBody
    public BookDTO updateBook(@RequestBody BookDTO bookUpdateDTO) throws EmptyFieldException, EntityNotFoundException {
        return bookService.updateBook(bookUpdateDTO);
    }

    @DeleteMapping("/delete-book/{bookId}")
    @ResponseBody
    public void deleteBook(@PathVariable String bookId) throws EntityNotFoundException {
        bookService.deleteBookById(bookId);
    }

    @GetMapping("/get-book/{bookId}")
    @ResponseBody
    public BookDTO getBookById(@PathVariable String bookId) throws EntityNotFoundException {
        return bookService.getBookById(bookId);
    }

    @GetMapping("/get-books")
    @ResponseBody
    public List<BookDTO> getBooks() {
        return bookService.getBooks();
    }

}