package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.validations.BookValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookDTO addBook(BookDTO bookDTO) throws EmptyFieldException{

        BookValidations.validateBookDTO(bookDTO, false);

        Book book = bookDTO.toBook(true);

        bookRepository.save(book);

        return new BookDTO(book);
    }

    public BookDTO updateBook(BookDTO bookUpdateDTO) throws EmptyFieldException, EntityNotFoundException {

        BookValidations.validateBookDTO(bookUpdateDTO, true);

        Optional<Book> oldBookOpt = bookRepository.findById(bookUpdateDTO.getId());
        if (oldBookOpt.isEmpty()) {
            throw new EntityNotFoundException("book");
        }

        Book newBook = oldBookOpt.get();
        newBook.setBookTitle(bookUpdateDTO.getBookTitle());
        newBook.setAuthor(bookUpdateDTO.getAuthor());
        newBook.setPublisher(bookUpdateDTO.getPublisher());
        newBook.setIsbn(bookUpdateDTO.getIsbn());
        newBook.setPublisherDate(bookUpdateDTO.getPublisherDate());
        bookRepository.save(newBook);

        return new BookDTO(newBook);
    }

    public void deleteBookById(String bookId) throws EntityNotFoundException {
        Optional<Book> bookToDelete = bookRepository.findById(bookId);
        if (bookToDelete.isEmpty()) {
            throw new EntityNotFoundException("book");
        }
        bookRepository.delete(bookToDelete.get());
    }

    public BookDTO getBookById(String bookId) throws EntityNotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new EntityNotFoundException("book");
        }
        return new BookDTO(book.get());
    }

    public List<BookDTO> getBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(book -> new BookDTO(book))
                .collect(Collectors.toList());
    }

}