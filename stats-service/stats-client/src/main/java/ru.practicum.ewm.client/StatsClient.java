package ru.practicum.ewm.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.dto.EndpointHitRequest;

import java.util.List;


public class StatsClient extends HttpHelper {

    public StatsClient(String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public void saveHit(EndpointHitRequest hit) {
        send(HttpMethod.POST, "/hit", null, hit);
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