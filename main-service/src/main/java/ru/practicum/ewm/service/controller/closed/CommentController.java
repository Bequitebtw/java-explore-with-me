package ru.practicum.ewm.service.controller.closed;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.dto.CommentRequest;
import ru.practicum.ewm.service.model.Comment;
import ru.practicum.ewm.service.service.CommentService;

@RestController("userCommentController")
@RequiredArgsConstructor
@RequestMapping("/users")
public class CommentController {
    private final StatsClient statsClient;
    private final RequestUtils requestUtils;
    private final CommentService commentService;

    //Создание комментария
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/comments")
    public Comment createComment(@PathVariable Integer userId, @RequestParam Integer eventId, @RequestBody @Valid CommentRequest commentRequest, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return commentService.createComment(userId, eventId, commentRequest, request.getRemoteAddr());
    }

    //Удаление комментария(может удалить или владелец или тот кто оставил)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/comments/{commentId}")
    public void deleteComment(@PathVariable Integer userId, @PathVariable Integer commentId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        commentService.deleteComment(userId, commentId);
    }

    //Редактирование комментария(только для владельца комментария)
    @PatchMapping("/{userId}/comments/{commentId}")
    public Comment updateEventRequestStatus(@PathVariable Integer userId,
                                            @PathVariable Integer commentId,
                                            @RequestBody @Valid CommentRequest comment,
                                            HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return commentService.updateComment(commentId, userId, comment);
    }

}
