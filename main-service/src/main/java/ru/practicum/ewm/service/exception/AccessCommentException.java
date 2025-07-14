package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AccessCommentException extends RuntimeException{
    private final Integer userId;
    private final Integer commentId;
    private final String reason = "No access to Object";
    @Override
    public String getMessage() {
        return String.format("User=%s have no access to comment=%s", userId, commentId);
    }
}
