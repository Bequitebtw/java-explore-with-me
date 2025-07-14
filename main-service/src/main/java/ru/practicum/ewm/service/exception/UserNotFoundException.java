package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserNotFoundException extends RuntimeException {
    private final Integer userId;
    private final String reason = "The required object was not found.";

    @Override
    public String getMessage() {
        return String.format("User with id=%s was not found", userId);
    }
}
