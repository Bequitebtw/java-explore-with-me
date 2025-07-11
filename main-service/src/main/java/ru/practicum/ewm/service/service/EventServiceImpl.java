package ru.practicum.ewm.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.*;
import ru.practicum.ewm.service.exception.*;
import ru.practicum.ewm.service.filters.EventSpecifications;
import ru.practicum.ewm.service.mapper.EventMapper;
import ru.practicum.ewm.service.model.Category;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.EventView;
import ru.practicum.ewm.service.model.User;
import ru.practicum.ewm.service.model.enums.EventStateAction;
import ru.practicum.ewm.service.model.enums.EventStateActionUser;
import ru.practicum.ewm.service.model.enums.EventStatus;
import ru.practicum.ewm.service.repository.CategoryRepository;
import ru.practicum.ewm.service.repository.EventRepository;
import ru.practicum.ewm.service.repository.EventViewRepository;
import ru.practicum.ewm.service.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventViewRepository eventViewRepository;

    @Transactional
    @Override
    public EventFullDto createEvent(NewEventDto event, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Category category = categoryRepository.findById(event.getCategory()).orElseThrow(
                () -> new CategoryNotFoundException(event.getCategory()));
        if (LocalDateTime.now().plusHours(2).isAfter(event.getEventDate())) {
            throw new EventDateTimeException(event.getEventDate());
        }

        return EventMapper.mapToEventFullDto(eventRepository.save(EventMapper.mapToEvent(event, category, user)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> findUserEvents(Integer userId, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return eventRepository.findByInitiatorId(userId, PageRequest.of(from / size, size)).stream()
                .map(EventMapper::mapToEventShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto findUserEvent(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (event.getInitiator().getId() != userId) {
            throw new RuntimeException();
        }
        return EventMapper.mapToEventFullDto(event);
    }

    @Transactional
    @Override
    public EventFullDto editEvent(EventEditRequest request, Integer eventId, Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (event.getInitiator().getId() != userId) {
            throw new AccessUserException(userId, eventId);
        }
        if (event.getState().equals(EventStatus.PUBLISHED)) {
            throw new IncorrectStateException(event.getState().toString());
        }
        if (request.getCategory() != null) {
            Category category = categoryRepository.findById(request.getCategory()).orElseThrow(() -> new CategoryNotFoundException(request.getCategory()));
            event.setCategory(category);
        }
        if (request.getEventDate() != null) {
            if (LocalDateTime.now().plusHours(2).isAfter(request.getEventDate())) {
                throw new EventDateTimeException(request.getEventDate());
            } else {
                event.setEventDate(request.getEventDate());
            }
        }
        if (request.getStateAction() != null) {
            if(request.getStateAction().equals(EventStateActionUser.CANCEL_REVIEW)){
                event.setState(EventStatus.CANCELED);
            }
            if(request.getStateAction().equals(EventStateActionUser.SEND_TO_REVIEW)) {
                event.setState(EventStatus.PENDING);
            }
        }
        return EventMapper.mapToEventFullDto(eventRepository.save(EventMapper.updateEvent(request, event)));
    }

    @Transactional
    @Override
    public EventFullDto findEventById(Integer eventId, String ip) {
        Event event = eventRepository.findByIdAndState(eventId, EventStatus.PUBLISHED);
        if (event == null) {
            throw new EventNotFoundException(eventId);
        }
        if (!eventViewRepository.existsByIpAndEventId(ip, eventId)) {
            EventView eventView = new EventView();
            eventView.setIp(ip);
            eventView.setEventId(eventId);
            eventViewRepository.save(eventView);
            event.setViews(event.getViews() + 1);
        }

        eventRepository.save(event);
        return EventMapper.mapToEventFullDto(event);
    }

    @Transactional
    @Override
    public EventFullDto updateEvent(UpdateEventRequest request, Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (request.getCategory() != null) {
            Category category = categoryRepository.findById(request.getCategory()).orElseThrow(() -> new CategoryNotFoundException(request.getCategory()));
            event.setCategory(category);
        }
        if (event.getState() != EventStatus.PENDING) {
            throw new IncorrectStateException(event.getState().toString());
        }
        if (request.getEventDate() != null) {
            if (LocalDateTime.now().plusHours(1).isAfter(request.getEventDate())) {
                throw new EventDateTimeException(request.getEventDate());
            } else {
                event.setEventDate(request.getEventDate());
            }
        } else {
            if (LocalDateTime.now().plusHours(1).isAfter(event.getEventDate())) {
                throw new EventDateTimeException(event.getEventDate());
            }
        }
        if (request.getStateAction() != null) {
            if (request.getStateAction().equals(EventStateAction.PUBLISH_EVENT)) {
                event.setState(EventStatus.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            }
            if (request.getStateAction().equals(EventStateAction.REJECT_EVENT)) {
                event.setState(EventStatus.CANCELED);
            }
        }

        return EventMapper.mapToEventFullDto(eventRepository.save(EventMapper.updateEvent(event, request)));
    }

    @Transactional
    @Override
    public List<EventFullDto> findEvents(List<Integer> users, List<String> states,
                                         List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {

        Specification<Event> spec = EventSpecifications.withUsers(users)
                .and(EventSpecifications.withStates(states))
                .and(EventSpecifications.withCategories(categories))
                .and(EventSpecifications.withDateRange(rangeStart, rangeEnd));

        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findAll(spec, pageable).getContent();

        return events.stream()
                .map(EventMapper::mapToEventFullDto)
                .collect(Collectors.toList());
    }

    public List<EventFullDto> searchEvents(String text,
                                           List<Integer> categories,
                                           Boolean paid,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Boolean onlyAvailable,
                                           String sort,
                                           Integer from,
                                           Integer size) {

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new IllegalArgumentException("rangeStart must be before rangeEnd");
        }

        Specification<Event> spec = EventSpecifications.withText(text)
                .and(EventSpecifications.withCategories(categories))
                .and(EventSpecifications.withPaid(paid))
                .and(EventSpecifications.withDateRange(rangeStart, rangeEnd))
                .and(EventSpecifications.withOnlyAvailable(onlyAvailable));

        Pageable pageable;
        if ("VIEWS".equalsIgnoreCase(sort)) {
            pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "views"));
        } else {
            pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "eventDate"));
        }

        List<Event> events = eventRepository.findAll(spec, pageable).getContent();

        eventRepository.saveAll(events);
        return events.stream().map(EventMapper::mapToEventFullDto).toList();
    }


}
