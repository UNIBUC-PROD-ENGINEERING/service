package com.bookstore.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDoubleRange extends RuntimeException {
    private static final String invalidDoubleRangeTemplate = "Field: %s must be between %f and %f";

    public InvalidDoubleRange(String field, Double min, Double max) {
        super(String.format(invalidDoubleRangeTemplate, field, min, max));
    }
}