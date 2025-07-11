package ru.practicum.ewm.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.server.model.EndpointHit;
import ru.practicum.ewm.server.service.StatsService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public void saveHit(@RequestBody @Valid EndpointHit hit) {
        log.info(String.format("Получен hit: app=%s, uri=%s, ip=%s, timestamp=%s",
                hit.getApp(), hit.getUri(), hit.getIp(), hit.getTimestamp()));
        statsService.saveHit(hit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end,
                                    @RequestParam(name = "uris", required = false) List<String> uris,
                                    @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        log.info(String.format("Запрос на статистику : start=%s, end=%s, uris=%s, unique=%s",
                start, end, uris, unique));
        return statsService.getStats(start, end, uris, unique);
    }
}
