package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestNotFoundException extends RuntimeException {
    private final Integer requestId;
    private final String reason = "The required object was not found.";

    @Override
    public String getMessage() {
        return String.format("Request with id=%s was not found", requestId);
    }
}
