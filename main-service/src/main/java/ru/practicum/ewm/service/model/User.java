package ru.practicum.ewm.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.annotation.StrictEmail;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @NotNull
    @NotBlank
    @StrictEmail
    @Size(min = 6, max = 254)
    private String email;
    @Column
    @NotNull
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
