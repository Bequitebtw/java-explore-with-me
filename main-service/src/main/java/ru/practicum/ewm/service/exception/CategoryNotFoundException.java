package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryNotFoundException extends RuntimeException {
    private final Integer categoryId;
    private final String reason = "The required object was not found.";

    @Override
    public String getMessage() {
        return String.format("Category with id=%s was not found", categoryId);
    }
}
