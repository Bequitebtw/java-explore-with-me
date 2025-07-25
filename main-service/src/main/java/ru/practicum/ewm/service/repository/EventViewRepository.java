package ru.practicum.ewm.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.model.EventView;

@Repository
public interface EventViewRepository extends JpaRepository<EventView, Integer> {
    boolean existsByIpAndEventId(String ip, Integer eventId);
}