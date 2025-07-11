package ru.practicum.ewm.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.CompilationDto;
import ru.practicum.ewm.service.dto.CompilationRequest;
import ru.practicum.ewm.service.exception.CompilationNotFoundException;
import ru.practicum.ewm.service.mapper.CompilationMapper;
import ru.practicum.ewm.service.model.Compilation;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.repository.CompilationRepository;
import ru.practicum.ewm.service.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CompilationDto createCompilation(CompilationRequest compilationRequest) {
        List<Event> events = eventRepository.findByIdIn(compilationRequest.getEvents());
        Compilation compilation = new Compilation();
        compilation.setEvents(events);
        compilation.setPinned(compilationRequest.getPinned());
        compilation.setTitle(compilationRequest.getTitle());
        return CompilationMapper.mapToCompilation(compilationRepository.save(compilation));
    }

    @Transactional
    @Override
    public void deleteCompilationById(Integer compilationId) {
        compilationRepository.findById(compilationId).orElseThrow(() -> new CompilationNotFoundException(compilationId));
        compilationRepository.deleteById(compilationId);
    }

    @Transactional
    @Override
    public CompilationDto updateCompilations(Integer compId, CompilationRequest compilationRequest) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        List<Event> events = eventRepository.findByIdIn(compilationRequest.getEvents());
        compilation.setEvents(events);
        compilation.setPinned(compilationRequest.getPinned());
        compilation.setTitle(compilationRequest.getTitle());
        return CompilationMapper.mapToCompilation(compilationRepository.save(compilation));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompilationDto> findCompilations(Boolean pinned, Integer from, Integer size) {
        if (pinned == null) {
            return compilationRepository.findAll(PageRequest.of(from / size, size))
                    .stream()
                    .map(CompilationMapper::mapToCompilation)
                    .collect(Collectors.toList());
        }
        return compilationRepository.findByPinned(pinned, PageRequest.of(from / size, size))
                .stream()
                .map(CompilationMapper::mapToCompilation)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CompilationDto findCompilationById(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        return CompilationMapper.mapToCompilation(compilation);
    }

}
