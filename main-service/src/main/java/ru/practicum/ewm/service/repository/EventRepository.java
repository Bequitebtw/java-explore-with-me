package ru.practicum.ewm.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.model.Event;
import ru.practicum.ewm.service.model.State;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findByInitiatorId(Integer initiatorId, Pageable pageable);

    Event findByIdAndState(Integer eventId, State state);

    List<Event> findByIdIn(List<Integer> ids);

}
