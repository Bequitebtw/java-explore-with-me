package ru.practicum.ewm.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;

@Configuration
public class MainConfig {

    @Value("${stats.server.url}")
    private String statsServerUrl;

    @Value("${app.name}")
    private String appName;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public StatsClient statsClient() {
        return new StatsClient(statsServerUrl, restTemplateBuilder());
    }

    @Bean
    public RequestUtils requestUtils() {
        return new RequestUtils(appName);
    }
}