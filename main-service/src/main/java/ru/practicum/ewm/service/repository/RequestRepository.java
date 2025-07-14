package ru.practicum.ewm.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByRequesterId(Integer userId);

    Request findByRequesterIdAndEventId(Integer requesterId, Integer eventId);

    List<Request> findAllByEventId(Integer eventId);

    List<Request> findByIdIn(List<Integer> ids);
}
