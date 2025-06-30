package ru.practicum.ewm.client;

import jakarta.annotation.Nullable;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class HttpHelper {
    private final RestTemplate rest;

    public HttpHelper(RestTemplate rest) {
        this.rest = rest;
    }

    public <T> ResponseEntity<Object> send(HttpMethod method, String path, @Nullable Map<String, Object> params, @Nullable T body) {
        HttpEntity<T> entity = new HttpEntity<>(body, defaultHeaders());
        try {
            if (params != null) {
                return rest.exchange(path, method, entity, Object.class, params);
            } else {
                return rest.exchange(path, method, entity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}