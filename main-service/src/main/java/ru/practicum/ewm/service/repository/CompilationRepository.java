package ru.practicum.ewm.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.model.Compilation;

import java.util.List;


@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    List<Compilation> findByPinned(Boolean pinned, Pageable pageable);
}
