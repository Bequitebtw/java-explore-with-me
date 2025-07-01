package ru.practicum.ewm.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.client.StatsClient;

@Configuration
public class MainConfig {

    @Value("${stats.server.url}")
    private String statsServerUrl;

    @Bean
    public StatsClient statsClient(RestTemplateBuilder restTemplateBuilder) {
        return new StatsClient(statsServerUrl, restTemplateBuilder);
    }
}