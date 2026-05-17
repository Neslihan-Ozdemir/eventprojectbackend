package com.example.eventproject.repository;

import com.example.eventproject.entity.Event;
import com.example.eventproject.util.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByStatus(EventStatus status, Pageable pageable);
    Page<Event> findByOwner_Id(Long ownerId, Pageable pageable);
    Optional<Event> findByIdAndOwner_Id(Long id, Long ownerId);
    Page<Event> findByTitleContainsOrDescriptionContainsOrLocationContainsOrCategoryContainsAllIgnoreCase(
            String title,
            String description,
            String location,
            String category,
            Pageable pageable
    );
    List<Event> findByStatusInAndDateBefore(List<EventStatus> statuses, LocalDate date);
    List<Event> findByStatusInAndDateEqualsAndTimeBefore(List<EventStatus> statuses, LocalDate date, LocalTime time);
}
