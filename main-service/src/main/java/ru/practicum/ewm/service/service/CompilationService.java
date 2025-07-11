package ru.practicum.ewm.service.service;

import ru.practicum.ewm.service.dto.CompilationDto;
import ru.practicum.ewm.service.dto.CompilationRequest;
import ru.practicum.ewm.service.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(CompilationRequest compilationRequest);

    void deleteCompilationById(Integer compilationId);

    CompilationDto updateCompilations(Integer compId, UpdateCompilationRequest compilationRequest);

    List<CompilationDto> findCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto findCompilationById(Integer compId);
}
