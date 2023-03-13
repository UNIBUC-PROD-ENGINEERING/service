package com.bookstore.v1.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateObjectException extends RuntimeException {
    private static final String duplicateObjectTemplate = "Object: %s already exists";

    public DuplicateObjectException(String object) {
        super(String.format(duplicateObjectTemplate, object));
    }
}
