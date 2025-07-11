package ru.practicum.ewm.service.mapper;

import ru.practicum.ewm.service.dto.*;
import ru.practicum.ewm.service.model.Category;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.User;

import java.time.LocalDateTime;

public class EventMapper {
    public static Event mapToEvent(NewEventDto eventDto, Category category, User user) {
        Event event = new Event();
        event.setAnnotation(eventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(eventDto.getDescription());
        event.setInitiator(user);
        event.setEventDate(eventDto.getEventDate());
        event.setLocation(eventDto.getLocation());
        event.setPaid(eventDto.getPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setTitle(eventDto.getTitle());
        return event;
    }

    public static Event updateEvent(Event event, UpdateEventRequest eventDto) {
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getEventDate() != null) {
            if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new RuntimeException();
            } else {
                event.setEventDate(eventDto.getEventDate());
            }
        }
        if (eventDto.getLocation() != null) {
            event.setLocation(eventDto.getLocation());
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        return event;
    }

    public static EventShortDto mapToEventShortDto(Event event) {
        UserShortDto user = new UserShortDto();
        user.setId(event.getInitiator().getId());
        user.setName(event.getInitiator().getName());
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(event.getCategory());
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setId(event.getId());
        eventShortDto.setInitiator(user);
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(event.getViews());
        return eventShortDto;
    }

    public static EventFullDto mapToEventFullDto(Event event) {
        UserShortDto user = new UserShortDto();
        user.setId(event.getInitiator().getId());
        user.setName(event.getInitiator().getName());
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(event.getCategory());
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setInitiator(user);
        eventFullDto.setLocation(event.getLocation());
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setEventStatus(event.getState());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setViews(event.getViews());
        return eventFullDto;
    }

    public static Event updateEvent(EventEditRequest eventEditRequest, Event event) {
        if (eventEditRequest.getAnnotation() != null) {
            event.setAnnotation(eventEditRequest.getAnnotation());
        }
        if (eventEditRequest.getDescription() != null) {
            event.setDescription(eventEditRequest.getDescription());
        }
        if (eventEditRequest.getEventDate() != null) {
            event.setEventDate(eventEditRequest.getEventDate());
        }
        if (eventEditRequest.getLocation() != null) {
            event.setLocation(eventEditRequest.getLocation());
        }
        if (eventEditRequest.getPaid() != null) {
            event.setPaid(eventEditRequest.getPaid());
        }
        if (eventEditRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(eventEditRequest.getParticipantLimit());
        }
        if (eventEditRequest.getRequestModeration() != null) {
            event.setRequestModeration(eventEditRequest.getRequestModeration());
        }
        if (eventEditRequest.getTitle() != null) {
            event.setTitle(eventEditRequest.getTitle());
        }
        return event;
    }
}
