package org.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String object, Object field, Object value) {
        super(String.format("%s with %s %s already exists", object, field, value));
    }
}
