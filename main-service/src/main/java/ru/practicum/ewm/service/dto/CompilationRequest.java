package ru.practicum.ewm.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompilationRequest {
    private List<Integer> events;
    @NotNull
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;
}
