package ru.practicum.ewm.service.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.model.Comment;
import ru.practicum.ewm.service.service.CommentService;

import java.util.List;


@RequiredArgsConstructor
@RestController("adminCommentController")
@RequestMapping("/admin")
public class CommentController {
    private final StatsClient statsClient;
    private final RequestUtils requestUtils;
    private final CommentService commentService;

    //Получение всех комментариев поста(под вопросом)
    @GetMapping("/comments/{commentId}")
    public Comment findCommentById(@PathVariable Integer commentId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return commentService.findCommentById(commentId);
    }

    //Получение комментариев определенного ивента
    @GetMapping("/events/{eventId}/comments")
    public List<Comment> findEventComments(@PathVariable Integer eventId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return commentService.findEventComments(eventId);
    }

    //Удаление комментария админом
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Integer commentId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        commentService.deleteCommentByAdmin(commentId);
    }
}
