package ru.practicum.ewm.service.mapper;

import ru.practicum.ewm.service.dto.CompilationDto;
import ru.practicum.ewm.service.model.Compilation;

import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDto mapToCompilation(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setEvents(compilation.getEvents().stream().map(EventMapper::mapToEventShortDto).collect(Collectors.toList()));
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setPinned(compilation.getPinned());
        return compilationDto;
    }
}
