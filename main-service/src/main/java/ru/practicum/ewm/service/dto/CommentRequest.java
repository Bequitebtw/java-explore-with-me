package ru.practicum.ewm.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    @NotNull
    @NotBlank
    @Size(max = 500, message = "Комментарий не может быть больше 500 символов")
    private String text;
}
