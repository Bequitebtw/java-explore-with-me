package ru.practicum.ewm.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import ru.practicum.ewm.service.model.EventRequestStatus;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateRequest {
    @NotNull
    @UniqueElements
    private List<Integer> requestIds;
    @NotNull
    private EventRequestStatus status;
}
