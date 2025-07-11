package ru.practicum.ewm.service.controller.closed;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.service.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.service.dto.RequestDto;
import ru.practicum.ewm.service.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RequestController {
    private final RequestUtils requestUtils;
    private final StatsClient statsClient;
    private final RequestService requestService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/requests")
    public RequestDto createRequest(@PathVariable Integer userId, @RequestParam Integer eventId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Integer userId, @PathVariable Integer requestId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getUserRequests(@PathVariable Integer userId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return requestService.findUserRequests(userId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getUserEventRequests(@PathVariable Integer userId, @PathVariable Integer eventId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return requestService.findUserEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequestStatus(@PathVariable Integer userId,
                                                                   @PathVariable Integer eventId,
                                                                   @RequestBody @Valid EventRequestStatusUpdateRequest update,
                                                                   HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return requestService.updateEventRequestStatus(update, userId, eventId);
    }
}
