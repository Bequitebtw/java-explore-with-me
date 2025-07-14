package ru.practicum.ewm.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Table(name = "event_views")
@Entity
@Setter
@Getter
public class EventView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column
    private String ip;
    @Column
    private Integer eventId;
    @Column
    private LocalDateTime viewedAt = LocalDateTime.now();
}