package ru.practicum.ewm.service.mapper;

import ru.practicum.ewm.service.dto.CommentRequest;
import ru.practicum.ewm.service.model.Comment;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.User;

public class CommentMapper {
    public static Comment mapToComment(CommentRequest commentRequest, Event event, User author) {
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setEvent(event);
        comment.setAuthor(author);
        return comment;
    }
}
