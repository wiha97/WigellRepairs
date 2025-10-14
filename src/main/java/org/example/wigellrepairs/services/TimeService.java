package org.example.wigellrepairs.services;

public interface TimeService {
    String formatted(long date, String format);
    Long getCurrentUTC();
}
