package ru.practicum.ewm.service.controller.closed;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.dto.EventEditRequest;
import ru.practicum.ewm.service.dto.EventFullDto;
import ru.practicum.ewm.service.dto.EventShortDto;
import ru.practicum.ewm.service.dto.NewEventDto;
import ru.practicum.ewm.service.service.EventService;

import java.util.List;

@RestController("userEventController")
@RequiredArgsConstructor
@RequestMapping("/users")
public class EventController {
    private final EventService eventService;
    private final RequestUtils requestUtils;
    private final StatsClient statsClient;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(@RequestBody @Valid NewEventDto newEventDto, @PathVariable Integer userId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return eventService.createEvent(newEventDto, userId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> findUserEvents(@PathVariable Integer userId, @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "10") Integer size, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return eventService.findUserEvents(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findUserEvent(@PathVariable Integer userId, @PathVariable Integer eventId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return eventService.findUserEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto editUserEvent(@RequestBody EventEditRequest event, @PathVariable Integer userId, @PathVariable Integer eventId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return eventService.editEvent(event, eventId, userId);
    }

}
