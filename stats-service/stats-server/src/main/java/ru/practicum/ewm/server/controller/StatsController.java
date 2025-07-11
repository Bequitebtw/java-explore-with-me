package ru.practicum.ewm.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public void saveHit(@RequestBody @Valid EndpointHit hit) {
        log.info(String.format("Получен hit: app=%s, uri=%s, ip=%s, timestamp=%s",
                hit.getApp(), hit.getUri(), hit.getIp(), hit.getTimestamp()));
        statsService.saveHit(hit);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end,
                                      @RequestParam(name = "uris", required = false) List<String> uris,
                                      @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        log.info(String.format("Запрос на статистику : start=%s, end=%s, uris=%s, unique=%s",
                start, end, uris, unique));
        List<ViewStats> stats = statsService.getStats(start, end, uris, unique);
        if (stats != null) {
            return ResponseEntity.ok(stats);
        }
        return ResponseEntity.badRequest().body("Invalid date range: start must be before end.");
    }
}
