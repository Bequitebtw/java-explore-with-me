package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RepeatedRequestException extends RuntimeException {
    private final Integer userId;
    private final Integer eventId;
    private final String reason = "Duplicated request";

    @Override
    public String getMessage() {
        return String.format("User=%s has already made a request from event=%s", userId, eventId);
    }
}
