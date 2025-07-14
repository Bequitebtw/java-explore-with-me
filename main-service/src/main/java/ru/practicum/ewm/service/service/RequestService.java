package ru.practicum.ewm.service.service;

import ru.practicum.ewm.service.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.service.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.service.dto.RequestDto;

import java.util.List;


public interface RequestService {
    RequestDto createRequest(Integer userId, Integer eventId);

    RequestDto cancelRequest(Integer userId, Integer eventId);

    List<RequestDto> findUserRequests(Integer userId);

    List<RequestDto> findUserEventRequests(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult updateEventRequestStatus(EventRequestStatusUpdateRequest request, Integer userId, Integer eventId);
}
