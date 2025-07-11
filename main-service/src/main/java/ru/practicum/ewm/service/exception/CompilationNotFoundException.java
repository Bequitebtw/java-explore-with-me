package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CompilationNotFoundException extends RuntimeException {
    private final Integer compilationId;
    private final String reason = "The required object was not found.";

    @Override
    public String getMessage() {
        return String.format("Compilation with id=%s was not found", compilationId);
    }
}
