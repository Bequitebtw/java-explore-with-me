package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ClosedCommentsException extends RuntimeException {
    private final Integer eventId;
    private final Integer userId;
    private final String reason = "Comments are not allowed";

    @Override
    public String getMessage() {
        return String.format("Пользователь %s запретил оставлять комментарии под постом %s", userId, eventId);
    }
}
