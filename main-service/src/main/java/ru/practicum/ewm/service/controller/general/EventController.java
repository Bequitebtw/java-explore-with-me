package ru.practicum.ewm.service.controller.general;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.dto.EventFullDto;
import ru.practicum.ewm.service.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController("publicEventController")
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final RequestUtils requestUtils;
    private final StatsClient statsClient;
    private final EventService eventService;

    @GetMapping("/{id}")
    public EventFullDto findEventById(@PathVariable Integer id, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return eventService.findEventById(id, request.getRemoteAddr());
    }

    @GetMapping
    public List<EventFullDto> searchEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "EVENT_DATE") String sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {

        statsClient.saveHit(requestUtils.createHit(request));

        return eventService.searchEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }
}
