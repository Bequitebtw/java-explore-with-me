package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.dto.EndpointHitRequest;

import java.util.List;

@Service
public class StatsClient extends HttpHelper {

    @Autowired
    public StatsClient(@Value("${stats.server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createHit(EndpointHitRequest hit) {
        return send(HttpMethod.POST, "/hit", null, hit);
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, boolean unique) {
        String uri = "/stats?start=" + start + "&end=" + end;

        if (!uris.isEmpty()) {
            uri += "&uris=" + String.join(",", uris);
        }

        uri += "&unique=" + unique;

        return send(HttpMethod.GET, uri, null, null);
    }

}