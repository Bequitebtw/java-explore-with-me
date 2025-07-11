package ru.practicum.ewm.service.mapper;

import ru.practicum.ewm.service.dto.RequestDto;
import ru.practicum.ewm.service.model.Request;

public class RequestMapper {
    public static RequestDto mapToRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setRequester(request.getRequester().getId());
        requestDto.setEvent(request.getEvent().getId());
        requestDto.setCreated(request.getCreated());
        requestDto.setRequestStatus(request.getRequestStatus());
        return requestDto;
    }
}
