package ru.practicum.ewm.server.service;

import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.server.model.EndpointHit;

import java.util.List;

public interface StatsService {

    public void saveHit(EndpointHit endpointHit);

    public List<ViewStats> getStats(String start, String end, List<String> uris, boolean unique);
}
