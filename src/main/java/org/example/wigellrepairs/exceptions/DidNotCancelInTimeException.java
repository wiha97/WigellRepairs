package org.example.wigellrepairs.exceptions;

public class DidNotCancelInTimeException extends RuntimeException {
    public DidNotCancelInTimeException(String date) {
        super(String.format("Could not cancel ticket, less than 24hrs remain: %s", date));
    }
}
