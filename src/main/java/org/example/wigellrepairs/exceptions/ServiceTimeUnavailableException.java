package org.example.wigellrepairs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ServiceTimeUnavailableException extends RuntimeException {
    public ServiceTimeUnavailableException(String category, String startDate, String end){
        super(String.format("No time available for %s services between %s and %s (UTC)", category, startDate, end));
    }
}
