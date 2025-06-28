package ru.practicum.ewm.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.dto.ViewStatsProjection;
import ru.practicum.ewm.server.model.EndpointHit;
import ru.practicum.ewm.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;


    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void saveHit(EndpointHit endpointHit) {

        statsRepository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        List<ViewStatsProjection> projections;
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("время начала не может быть после времени окончания");
        }
        if (unique) {
            projections = statsRepository.findUniqueStatsProjection(startTime, endTime, uris);
        } else {
            projections = statsRepository.findStatsProjection(startTime, endTime, uris);
        }

        return projections.stream()
                .map(p -> new ViewStats(p.getApp(), p.getUri(), p.getHits()))
                .collect(Collectors.toList());
    }
}
