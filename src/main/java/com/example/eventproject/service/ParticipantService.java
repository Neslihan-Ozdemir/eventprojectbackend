package com.example.eventproject.service;


import com.example.eventproject.dto.ParticipantResponseDto;
import com.example.eventproject.dto.UsersResponseDto;
import com.example.eventproject.entity.Event;
import com.example.eventproject.entity.Participant;
import com.example.eventproject.entity.Users;
import com.example.eventproject.repository.EventRepository;
import com.example.eventproject.repository.ParticipantRepository;
import com.example.eventproject.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    final ParticipantRepository participantRepository;
    final EventRepository eventRepository;
    final UsersRepository usersRepository;
    final HttpServletRequest request;

    private Optional<Users> getSessionUser() {
        UsersResponseDto dto = (UsersResponseDto) request.getSession().getAttribute("user");
        if (dto == null) return Optional.empty();
        return usersRepository.findById(dto.getId());
    }

    public ResponseEntity<?> join(Long eventId) {
        Optional<Users> optionalUser = getSessionUser();
        if (optionalUser.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Yetkisiz erişim.");
            return ResponseEntity.status(401).body(hm);
        }

        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Etkinlik bulunamadı.");
            return ResponseEntity.status(404).body(hm);
        }

        boolean alreadyJoined = participantRepository.existsByEvent_IdAndUsers_Id(eventId, optionalUser.get().getId());
        if (alreadyJoined) {
            Map<String, Object> hm = Map.of("success", false, "message", "Bu etkinliğe zaten katıldınız.");
            return ResponseEntity.badRequest().body(hm);
        }

        Participant participant = new Participant();
        participant.setEvent(optionalEvent.get());
        participant.setUsers(optionalUser.get());
        participantRepository.save(participant);

        Map<String, Object> hm = Map.of("success", true, "message", "Etkinliğe başarıyla katıldınız.");
        return ResponseEntity.ok().body(hm);
    }

    public ResponseEntity<?> listParticipants(Long eventId) {
        Optional<Users> optionalUser = getSessionUser();
        if (optionalUser.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Yetkisiz erişim.");
            return ResponseEntity.status(401).body(hm);
        }

        Optional<Event> optionalEvent = eventRepository.findByIdAndOwner_Id(eventId, optionalUser.get().getId());
        if (optionalEvent.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Etkinlik bulunamadı veya bu etkinliğin sahibi değilsiniz.");
            return ResponseEntity.status(404).body(hm);
        }

        List<Participant> participants = participantRepository.findByEvent_Id(eventId);
        List<ParticipantResponseDto> responseDtos = participants.stream().map(p -> {
            ParticipantResponseDto dto = new ParticipantResponseDto();
            dto.setUserId(p.getUsers().getId());
            dto.setUsername(p.getUsers().getUsername());
            dto.setEmail(p.getUsers().getEmail());
            return dto;
        }).toList();
        return ResponseEntity.ok().body(responseDtos);
    }

    public ResponseEntity<?> listMyParticipations() {
        Optional<Users> optionalUser = getSessionUser();
        if (optionalUser.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Yetkisiz erişim.");
            return ResponseEntity.status(401).body(hm);
        }
        List<Participant> participants = participantRepository.findByUsers_Id(optionalUser.get().getId());
        return ResponseEntity.ok().body(participants);
    }
}