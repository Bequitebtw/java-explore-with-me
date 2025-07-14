package ru.practicum.ewm.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.CommentRequest;
import ru.practicum.ewm.service.exception.*;
import ru.practicum.ewm.service.mapper.CommentMapper;
import ru.practicum.ewm.service.model.Comment;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.EventView;
import ru.practicum.ewm.service.model.User;
import ru.practicum.ewm.service.model.enums.EventStatus;
import ru.practicum.ewm.service.repository.CommentRepository;
import ru.practicum.ewm.service.repository.EventRepository;
import ru.practicum.ewm.service.repository.EventViewRepository;
import ru.practicum.ewm.service.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventViewRepository eventViewRepository;

    @Override
    @Transactional
    public Comment createComment(Integer userId, Integer eventId, CommentRequest commentRequest, String ip) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (event.getIsCommentsOpen().equals(Boolean.FALSE)) {
            throw new ClosedCommentsException(eventId, event.getInitiator().getId());
        }
        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new IncorrectStateException(event.getState().toString());
        }

        if (!eventViewRepository.existsByIpAndEventId(ip, eventId)) {
            EventView eventView = new EventView();
            eventView.setIp(ip);
            eventView.setEventId(eventId);
            eventViewRepository.save(eventView);
            event.setViews(event.getViews() + 1);
        }

        eventRepository.save(event);


        return commentRepository.save(CommentMapper.mapToComment(commentRequest, event, user));
    }

    @Override
    @Transactional
    public void deleteComment(Integer userId, Integer commentId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        if (comment.getEvent().getIsCommentsOpen().equals(Boolean.FALSE)) {
            throw new ClosedCommentsException(comment.getEvent().getId(), comment.getEvent().getInitiator().getId());
        }
        //System.out.println(userId + "\n" + comment.getEvent().getInitiator().getId() + "\n" + comment.getAuthor().getId());
        if (!userId.equals(comment.getEvent().getInitiator().getId()) &&
                !userId.equals(comment.getAuthor().getId())) {
            throw new AccessCommentException(userId, commentId);
        }
        commentRepository.deleteById(commentId);
    }

    @Transactional
    @Override
    public void deleteCommentByAdmin(Integer commentId) {
        commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findEventComments(Integer eventId) {
        eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        return commentRepository.findAllByEventId(eventId);
    }

    @Override
    @Transactional
    public Comment updateComment(Integer commentId, Integer userId, CommentRequest commentRequest) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        if (!Objects.equals(comment.getAuthor().getId(), userId)) {
            throw new AccessCommentException(userId, commentId);
        }
        if (comment.getEvent().getIsCommentsOpen().equals(Boolean.FALSE)) {
            throw new ClosedCommentsException(comment.getEvent().getId(), userId);
        }


        comment.setText(commentRequest.getText());
        comment.setIsEdited(true);
        return commentRepository.save(comment);

    }

    @Override
    public Comment findCommentById(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

}
