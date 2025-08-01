package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AccessEventException extends RuntimeException {
    private final Integer userId;
    private final Integer eventId;
    private final String reason = "No access to Object";

    @Override
    public String getMessage() {
        return String.format("User=%s have no access to event=%s", userId, eventId);
    }
}
