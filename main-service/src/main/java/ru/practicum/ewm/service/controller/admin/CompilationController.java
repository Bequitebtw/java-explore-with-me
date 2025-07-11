package ru.practicum.ewm.service.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.dto.CompilationDto;
import ru.practicum.ewm.service.dto.CompilationRequest;
import ru.practicum.ewm.service.service.CompilationService;

@RestController("adminCompilationController")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class CompilationController {
    private final StatsClient statsClient;
    private final RequestUtils requestUtils;
    private final CompilationService compilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/compilations")
    public CompilationDto createCompilation(@RequestBody @Valid CompilationRequest compilationRequest,
                                            HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return compilationService.createCompilation(compilationRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilationById(@PathVariable Integer compId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        compilationService.deleteCompilationById(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public CompilationDto updateCompilationById(@RequestBody @Valid CompilationRequest compilationRequest,
                                                @PathVariable Integer compId,
                                                HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return compilationService.updateCompilations(compId, compilationRequest);
    }

}
