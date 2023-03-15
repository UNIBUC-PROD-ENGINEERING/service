package com.bookstore.v1.validations;

import com.bookstore.v1.dto.BookDTO;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;

public class BookValidations {
    public static void validateBookDTO(BookDTO bookDTO, Boolean validateId) throws EmptyFieldException {
        if (validateId) {
            if (bookDTO.getId() == null) {
                throw new EmptyFieldException("id");
            }
        }
        if (bookDTO.getTitle() == null || bookDTO.getTitle().isEmpty()) {
            throw new EmptyFieldException("title");
        }
        if (bookDTO.getAuthor() == null || bookDTO.getAuthor().isEmpty()) {
            throw new EmptyFieldException("author");
        }
        if (bookDTO.getPublisher() == null || bookDTO.getPublisher().isEmpty()) {
            throw new EmptyFieldException("publisher");
        }
        if (bookDTO.getIsbn() == null || bookDTO.getIsbn().isEmpty()) {
            throw new EmptyFieldException("isbn");
        }
        if (bookDTO.getPublishedDate() == null) {
            throw new EmptyFieldException("publisher date");
        }
    }
}
