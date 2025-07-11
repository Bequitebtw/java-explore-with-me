package ru.practicum.ewm.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.model.Location;
import ru.practicum.ewm.service.model.StateAction;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateEventRequest {
    private String annotation;
    private Integer category;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    private String title;
}
