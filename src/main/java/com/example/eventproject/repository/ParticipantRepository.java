package com.example.eventproject.repository;

import com.example.eventproject.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByEvent_Id(Long eventId);
    List<Participant> findByUsers_Id(Long usersId);
    boolean existsByEvent_IdAndUsers_Id(Long eventId, Long usersId);
    long countByEvent_Id(Long eventId);
}
