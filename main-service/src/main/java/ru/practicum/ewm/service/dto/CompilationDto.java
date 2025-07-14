package ru.practicum.ewm.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CompilationDto {
    private Integer id;
    private Boolean pinned;
    private String title;
    private List<EventShortDto> events;
}
