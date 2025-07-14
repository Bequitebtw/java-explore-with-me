package ru.practicum.ewm.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.model.Category;
import ru.practicum.ewm.service.model.Comment;
import ru.practicum.ewm.service.model.Location;
import ru.practicum.ewm.service.model.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventFullDto {
    private Integer id;
    private String annotation;
    private Category category;
    private Integer ConfirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private Boolean isCommentsOpen;
    private List<Comment> comments;
    private EventStatus state;
    private String title;
    private Integer views;
}
