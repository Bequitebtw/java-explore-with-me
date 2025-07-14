package ru.practicum.ewm.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CompilationRequest {
    private List<Integer> events = new ArrayList<>();
    private Boolean pinned = false;
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
}
