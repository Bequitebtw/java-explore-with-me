package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LimitUserException extends RuntimeException {
    private final Integer limit;
    private final String reason = "For the requested operation the conditions are not met.";

    @Override
    public String getMessage() {
        return "The participant limit has been reached";
    }
}
