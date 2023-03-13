package com.bookstore.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyFieldException extends RuntimeException {
    private static final String emptyFieldTemplate = "Field: %s is empty";

    public EmptyFieldException(String field) {
        super(String.format(emptyFieldTemplate, field));
    }
}
