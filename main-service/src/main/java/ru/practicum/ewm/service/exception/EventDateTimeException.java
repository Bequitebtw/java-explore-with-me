package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public class EventDateTimeException extends RuntimeException {
    private final LocalDateTime localDateTime;
    private final String reason = "For the requested operation the conditions are not met.";

    @Override
    public String getMessage() {
        return String.format("Field: eventDate. Error: должно содержать дату," +
                        " которая еще не наступила. Value: %s",
                localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
