package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IncorrectStateException extends RuntimeException {
    private final String state;
    private final String reason = "For the requested operation the conditions are not met.";

    @Override
    public String getMessage() {
        return String.format("Event not in the right state: %s", state);
    }
}
