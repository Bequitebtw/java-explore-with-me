package ru.practicum.ewm.service.service;

import ru.practicum.ewm.service.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventFullDto createEvent(NewEventDto event, Integer userId);

    List<EventShortDto> findUserEvents(Integer userId, Integer from, Integer size);

    EventFullDto findUserEvent(Integer userId, Integer eventId);

    EventFullDto editEvent(EventEditRequest event, Integer eventId, Integer userId);

    EventFullDto findEventById(Integer eventId);

    EventFullDto updateEvent(UpdateEventRequest request, Integer eventId);

    List<EventFullDto> findEvents(List<Integer> users,
                                  List<String> states,
                                  List<Integer> categories,
                                  LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd,
                                  Integer from,
                                  Integer size);

    List<EventFullDto> searchEvents(String text,
                                    List<Integer> categories,
                                    Boolean paid,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Boolean onlyAvailable,
                                    String sort,
                                    Integer from,
                                    Integer size);
}
