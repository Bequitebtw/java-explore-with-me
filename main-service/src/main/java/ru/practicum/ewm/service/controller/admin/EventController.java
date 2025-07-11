package ru.practicum.ewm.service.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.dto.EventFullDto;
import ru.practicum.ewm.service.dto.UpdateEventRequest;
import ru.practicum.ewm.service.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class EventController {
    private final StatsClient statsClient;
    private final RequestUtils requestUtils;
    private final EventService eventService;

    @GetMapping("/events")
    public List<EventFullDto> findEvents(@RequestParam(required = false) List<Integer> users,
                                         @RequestParam(required = false) List<String> states,
                                         @RequestParam(required = false) List<Integer> categories,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return eventService.findEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto editEvent(@PathVariable Integer eventId, @RequestBody UpdateEventRequest event, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return eventService.updateEvent(event, eventId);
    }
}
