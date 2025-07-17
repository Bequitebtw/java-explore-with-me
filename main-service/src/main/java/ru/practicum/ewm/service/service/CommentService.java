package ru.practicum.ewm.service.service;


import ru.practicum.ewm.service.dto.CommentRequest;
import ru.practicum.ewm.service.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Integer userId, Integer eventId, CommentRequest commentRequest, String ip);

    void deleteComment(Integer userId, Integer commentId);

    void deleteCommentByAdmin(Integer commentId);

    List<Comment> findEventComments(Integer eventId);

    Comment updateComment(Integer commentId, Integer userId, CommentRequest comment);

    Comment findCommentById(Integer commentId);
}
