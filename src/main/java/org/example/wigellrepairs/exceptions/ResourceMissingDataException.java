package org.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceMissingDataException extends RuntimeException {
    public ResourceMissingDataException(String field) {
        super(String.format("Missing value for field %s", field));
    }
}
