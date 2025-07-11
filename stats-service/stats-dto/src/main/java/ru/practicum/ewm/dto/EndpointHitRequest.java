package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitRequest {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;

    public EndpointHitRequest(String app, String uri, String ip) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}