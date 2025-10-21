package org.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InPastException extends RuntimeException {
    public InPastException(String action, String date) {
        super(String.format("Time Machine required to %s in the past: %s", action, date));
    }
}
