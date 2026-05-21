package com.example.eventproject.service;


import com.example.eventproject.dto.*;
import com.example.eventproject.entity.Event;
import com.example.eventproject.entity.Users;
import com.example.eventproject.repository.EventRepository;
import com.example.eventproject.repository.ParticipantRepository;
import com.example.eventproject.repository.UsersRepository;
import com.example.eventproject.util.EventStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    final EventRepository eventRepository;
    final ParticipantRepository participantRepository;
    final UsersRepository usersRepository;
    final HttpServletRequest request;
    final ModelMapper modelMapper;

    private Optional<Users> getSessionUser() {
        UsersResponseDto dto = (UsersResponseDto) request.getSession().getAttribute("user");
        if (dto == null) return Optional.empty();
        return usersRepository.findById(dto.getId());
    }

    private ResponseEntity<Map<String, Object>> validateEventDateTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            Map<String, Object> hm = Map.of("success", false, "message", "Tarih ve saat zorunludur.");
            return ResponseEntity.badRequest().body(hm);
        }
        return null;
    }

    private ResponseEntity<Map<String, Object>> updateStatus(Long id, EventStatus status, String message) {
        Optional<Users> optionalUser = getSessionUser();
        if (optionalUser.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Yetkisiz erişim.");
            return ResponseEntity.status(401).body(hm);
        }
        Optional<Event> optionalEvent = eventRepository.findByIdAndOwner_Id(id, optionalUser.get().getId());
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setStatus(status);
            eventRepository.save(event);
            Map<String, Object> hm = Map.of("success", true, "message", message);
            return ResponseEntity.ok().body(hm);
        }
        Map<String, Object> hm = Map.of("success", false, "message", "Etkinlik bulunamadı.");
        return ResponseEntity.status(404).body(hm);
    }

    public ResponseEntity<?> create(EventCreateDto eventCreateRequestDto) {
        Optional<Users> optionalUser = getSessionUser();
        if (optionalUser.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Yetkisiz erişim.");
            return ResponseEntity.status(401).body(hm);
        }
        ResponseEntity<Map<String, Object>> validation = validateEventDateTime(eventCreateRequestDto.getDate(), eventCreateRequestDto.getTime());
        if (validation != null) return validation;

        Event event = modelMapper.map(eventCreateRequestDto, Event.class);
        event.setOwner(optionalUser.get());
        event.setStatus(EventStatus.PUBLISHED);
        event = eventRepository.save(event);
        EventResponseDto responseDto = modelMapper.map(event, EventResponseDto.class);
        responseDto.setOwnerId(event.getOwner().getId());
        responseDto.setOwnerName(event.getOwner().getUsername());
        return ResponseEntity.ok().body(responseDto);
    }

    public ResponseEntity<Map<String, Object>> update(EventUpdateDto eventUpdateDto) {
        Optional<Users> optionalUser = getSessionUser();
        if (optionalUser.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Yetkisiz erişim.");
            return ResponseEntity.status(401).body(hm);
        }
        ResponseEntity<Map<String, Object>> validation = validateEventDateTime(eventUpdateDto.getDate(), eventUpdateDto.getTime());
        if (validation != null) return validation;

        Optional<Event> optionalEvent = eventRepository.findByIdAndOwner_Id(eventUpdateDto.getId(), optionalUser.get().getId());
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setTitle(eventUpdateDto.getTitle());
            event.setDate(eventUpdateDto.getDate());
            event.setTime(eventUpdateDto.getTime());
            event.setLocation(eventUpdateDto.getLocation());
            event.setDescription(eventUpdateDto.getDescription());
            event.setCategory(eventUpdateDto.getCategory());
            eventRepository.save(event);
            Map<String, Object> hm = Map.of("success", true, "message", "Etkinlik başarıyla güncellendi.");
            return ResponseEntity.ok().body(hm);
        }
        Map<String, Object> hm = Map.of("success", false, "message", "Etkinlik bulunamadı.");
        return ResponseEntity.status(404).body(hm);
    }

    public ResponseEntity<Map<String, Object>> deleteOne(Long id) {
        Optional<Users> optionalUser = getSessionUser();
        if (optionalUser.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Yetkisiz erişim.");
            return ResponseEntity.status(401).body(hm);
        }
        Optional<Event> optionalEvent = eventRepository.findByIdAndOwner_Id(id, optionalUser.get().getId());
        if (optionalEvent.isPresent()) {
            participantRepository.deleteAll(participantRepository.findByEvent_Id(id));
            eventRepository.deleteById(id);
            Map<String, Object> hm = Map.of("success", true, "message", "Etkinlik başarıyla silindi.");
            return ResponseEntity.ok().body(hm);
        }
        Map<String, Object> hm = Map.of("success", false, "message", "Etkinlik bulunamadı.");
        return ResponseEntity.status(404).body(hm);
    }

    public ResponseEntity<Map<String, Object>> publish(Long id) {
        return updateStatus(id, EventStatus.PUBLISHED, "Etkinlik yayınlandı.");
    }

    public ResponseEntity<Map<String, Object>> pause(Long id) {
        return updateStatus(id, EventStatus.PAUSED, "Etkinlik yayını durduruldu.");
    }

    public ResponseEntity<Map<String, Object>> archive(Long id) {
        return updateStatus(id, EventStatus.ARCHIVED, "Etkinlik arşivlendi.");
    }

    public ResponseEntity<?> getDetail(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            Map<String, Object> hm = Map.of("success", false, "message", "Etkinlik bulunamadı.");
            return ResponseEntity.status(404).body(hm);
        }
        Event event = optionalEvent.get();
        EventDetailResponseDto responseDto = modelMapper.map(event, EventDetailResponseDto.class);
        responseDto.setOwnerId(event.getOwner().getId());
        responseDto.setOwnerName(event.getOwner().getUsername());
        responseDto.setParticipantCount(participantRepository.countByEvent_Id(event.getId()));
        return ResponseEntity.ok().body(responseDto);
    }

    public Page<EventResponseDto> listPublished(int page) {
        return eventRepository.findByStatus(EventStatus.PUBLISHED, PageRequest.of(page, 9))
                .map(event -> {
                    EventResponseDto dto = modelMapper.map(event, EventResponseDto.class);
                    dto.setOwnerId(event.getOwner().getId());
                    dto.setOwnerName(event.getOwner().getUsername());
                    return dto;
                });
    }

    public Page<EventResponseDto> listByOwner(int page) {
        Optional<Users> optionalUser = getSessionUser();
        if (optionalUser.isEmpty()) return Page.empty();
        return eventRepository.findByOwner_Id(optionalUser.get().getId(), PageRequest.of(page, 10))
                .map(event -> {
                    EventResponseDto dto = modelMapper.map(event, EventResponseDto.class);
                    dto.setOwnerId(event.getOwner().getId());
                    dto.setOwnerName(event.getOwner().getUsername());
                    return dto;
                });
    }

    public Page<EventResponseDto> search(String q, int page) {
        return eventRepository.findByTitleContainsOrDescriptionContainsOrLocationContainsOrCategoryContainsAllIgnoreCase(
                        q, q, q, q, PageRequest.of(page, 10))
                .map(event -> {
                    EventResponseDto dto = modelMapper.map(event, EventResponseDto.class);
                    dto.setOwnerId(event.getOwner().getId());
                    dto.setOwnerName(event.getOwner().getUsername());
                    return dto;
                });
    }

    public void archiveExpiredEvents() {
        LocalDate now = LocalDate.now();
        LocalTime time = LocalTime.now();
        List<EventStatus> statuses = List.of(EventStatus.PUBLISHED, EventStatus.PAUSED);

        List<Event> expiredByDate = eventRepository.findByStatusInAndDateBefore(statuses, now);
        List<Event> expiredByTime = eventRepository.findByStatusInAndDateEqualsAndTimeBefore(statuses, now, time);

        expiredByDate.forEach(event -> {
            event.setStatus(EventStatus.ARCHIVED);
            eventRepository.save(event);
        });

        expiredByTime.forEach(event -> {
            event.setStatus(EventStatus.ARCHIVED);
            eventRepository.save(event);
        });
    }
}