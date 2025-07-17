package ru.practicum.ewm.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentNotFoundException extends RuntimeException {
    private final Integer commentId;
    private final String reason = "The required object was not found.";


    @Override
    public String getMessage() {
        return String.format("Comment with id=%s was not found", commentId);
    }
}
