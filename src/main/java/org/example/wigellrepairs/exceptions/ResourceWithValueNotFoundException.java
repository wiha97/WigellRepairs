package org.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceWithValueNotFoundException extends RuntimeException {
    public ResourceWithValueNotFoundException(String field, Object value) {
        super(String.format("%s with value %s not found", field, value));
    }
}
