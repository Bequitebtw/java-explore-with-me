package ru.practicum.ewm.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.service.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.service.dto.RequestDto;
import ru.practicum.ewm.service.exception.*;
import ru.practicum.ewm.service.mapper.RequestMapper;
import ru.practicum.ewm.service.model.*;
import ru.practicum.ewm.service.model.enums.RequestStateAction;
import ru.practicum.ewm.service.model.enums.EventStatus;
import ru.practicum.ewm.service.model.enums.RequestStatus;
import ru.practicum.ewm.service.repository.EventRepository;
import ru.practicum.ewm.service.repository.RequestRepository;
import ru.practicum.ewm.service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Transactional
    @Override
    public RequestDto createRequest(Integer userId, Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new AccessUserException(userId, eventId);
        }
        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new IncorrectStateException(event.getState().toString());
        }

        if (event.getParticipantLimit() != 0) {
            if (Objects.equals(event.getConfirmedRequests(), event.getParticipantLimit())) {
                throw new LimitUserException(event.getParticipantLimit());
            }
        }

        if (requestRepository.findByRequesterIdAndEventId(userId, eventId) != null) {
            throw new RepeatedRequestException(userId, eventId);
        }

        Request request = new Request();


        if (!event.getRequestModeration()) {
            request.setRequestStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        request.setRequester(user);
        request.setEvent(event);
        eventRepository.save(event);
        return RequestMapper.mapToRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(Integer userId, Integer requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RequestNotFoundException(requestId));
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        request.setRequestStatus(RequestStatus.REJECTED);
        return RequestMapper.mapToRequestDto(requestRepository.save(request));
    }

    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> findUserRequests(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return requestRepository.findAllByRequesterId(userId).stream().map(RequestMapper::mapToRequestDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<RequestDto> findUserEventRequests(Integer userId, Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new AccessUserException(userId, eventId);
        }
        return requestRepository.findAllByEventId(eventId).stream().map(RequestMapper::mapToRequestDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult updateEventRequestStatus(EventRequestStatusUpdateRequest request, Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        if (!event.getRequestModeration()) {
            throw new RequestConflictException("Данное событие не нуждается в обработке заявок");
        }

        List<Request> requests = requestRepository.findAllByEventId(eventId);
        List<Request> requestList = requestRepository.findByIdIn(request.getRequestIds());
        Set<Integer> eventRequestIds = requests.stream()
                .map(Request::getId)
                .collect(Collectors.toSet());

        boolean allRequestsBelongToEvent = requestList.stream()
                .allMatch(req -> eventRequestIds.contains(req.getId()) && req.getRequestStatus() == RequestStatus.PENDING);

        if (!allRequestsBelongToEvent) {
            throw new RequestConflictException("Заявка уже принята или отклонена, либо не относится к событию с id = " + eventId);
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw new AccessUserException(userId, eventId);
        }

        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new LimitUserException(event.getParticipantLimit());
        }

        List<Request> confirmedRequests = new ArrayList<>();
        List<Request> rejectedRequests = new ArrayList<>();

        //если все id можно одобрить или отклонить
        if (request.getStatus().equals(RequestStateAction.REJECTED)) {
            requestList.forEach(elem -> elem.setRequestStatus(RequestStatus.REJECTED));
            rejectedRequests = requestList;
            requestRepository.saveAll(requestList);
        } else if (request.getRequestIds().size() + event.getConfirmedRequests() <= event.getParticipantLimit()
                && request.getStatus().equals(RequestStateAction.CONFIRMED)) {

            requestList.forEach(elem -> elem.setRequestStatus(RequestStatus.CONFIRMED));
            event.setConfirmedRequests(event.getConfirmedRequests() + requestList.size());
            confirmedRequests = requestList;
            requestRepository.saveAll(requestList);
        } else {
            //если несколько id нужно принять, а несколько отклонить
            for (Request req : requestList) {
                if (event.getParticipantLimit() > event.getConfirmedRequests()) {
                    req.setRequestStatus(RequestStatus.CONFIRMED);
                    confirmedRequests.add(req);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                } else {
                    req.setRequestStatus(RequestStatus.REJECTED);
                    rejectedRequests.add(req);
                }
            }
        }

        eventRepository.save(event);
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        eventRequestStatusUpdateResult.setConfirmedRequests(confirmedRequests.stream().map(RequestMapper::mapToRequestDto).collect(Collectors.toList()));
        eventRequestStatusUpdateResult.setRejectedRequests(rejectedRequests.stream().map(RequestMapper::mapToRequestDto).collect(Collectors.toList()));
        return eventRequestStatusUpdateResult;
    }


}
