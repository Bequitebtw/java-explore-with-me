package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.dto.EndpointHitRequest;

import java.util.List;

@Service
public class StatsClient extends BaseClient {
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
        return post("/hit", hit);
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, boolean unique) {
        StringBuilder request = new StringBuilder("/stats?start=" + start + "?end" + end + "?");
        if (!uris.isEmpty()) {
            request.append("uris=");
            for (int x = 0; x < uris.size(); x++) {
                if (x != uris.size() - 1) {
                    request.append(uris.get(x));
                    request.append(",");
                }
                request.append(uris.get(x));
            }
        }
        request.append("?unique=" + unique);
        return get(request.toString());
    }

}