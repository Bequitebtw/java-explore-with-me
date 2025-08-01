package ru.practicum.ewm.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.model.Location;
import ru.practicum.ewm.service.model.enums.EventStateActionUser;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventEditRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private EventStateActionUser stateAction;
    private Integer participantLimit;
    private Boolean isCommentsOpened;
    private Boolean requestModeration;
    @Size(min = 3, max = 120)
    private String title;
}