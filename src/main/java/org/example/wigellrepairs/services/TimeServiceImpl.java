package org.example.wigellrepairs.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {

	@Override
	public String formatted(long date, String format) {
        Instant instant = Instant.ofEpochSecond(date);
        ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(zdt);
	}

	@Override
	public Long getCurrentUTC() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
	}
}
