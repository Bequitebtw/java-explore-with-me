package ru.practicum.ewm.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.model.enums.RequestStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDto {
    private Integer id;
    private Integer event;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private Integer requester;
    private RequestStatus requestStatus;
}
