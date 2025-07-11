package ru.practicum.ewm.service.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Getter
@Setter
public class UpdateCompilationRequest {
    @UniqueElements
    private List<Integer> events;
    private Boolean pinned = false;
    @Size(min = 1, max = 50)
    private String title;
}
