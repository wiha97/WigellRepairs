package org.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
public class ValueOutOfRangeException extends RuntimeException {
    public ValueOutOfRangeException(String field, int length) {
        super(String.format("Exceeding max range of %s for %s", length, field));
    }
}
