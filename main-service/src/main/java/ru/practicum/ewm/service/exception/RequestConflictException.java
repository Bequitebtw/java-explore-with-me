package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestConflictException extends RuntimeException{
    private final String message;
    private final String reason = "The operation is not available for the request";
    @Override
    public String getMessage() {
        return String.format("Conflict: %s", message);
    }
}
