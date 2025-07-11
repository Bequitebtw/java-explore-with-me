package ru.practicum.ewm.service.controller.general;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.dto.CompilationDto;
import ru.practicum.ewm.service.service.CompilationService;

import java.util.List;

@RestController("publicCompilationController")
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final StatsClient statsClient;
    private final RequestUtils requestUtils;
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> findCompilations(@RequestParam(required = false) Boolean pinned,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size,
                                                 HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return compilationService.findCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto findCompilationById(@PathVariable Integer compId, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return compilationService.findCompilationById(compId);
    }

}
