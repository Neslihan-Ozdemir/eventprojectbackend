package com.example.eventproject.controller;


import com.example.eventproject.dto.JoinEventDto;
import com.example.eventproject.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {

    final ParticipantService participantService;

    @PostMapping("/join")
    public ResponseEntity join(@Valid @RequestBody JoinEventDto joinEventDto) {
        return participantService.join(joinEventDto.getEventId());
    }

    @GetMapping("/list/{eventId}")
    public ResponseEntity listParticipants(@PathVariable Long eventId) {
        return participantService.listParticipants(eventId);
    }

    @GetMapping("/my")
    public ResponseEntity listMyParticipations() {
        return participantService.listMyParticipations();
    }
}
