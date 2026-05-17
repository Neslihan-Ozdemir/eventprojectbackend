package com.example.eventproject.controller;

import com.example.eventproject.dto.*;
import com.example.eventproject.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

    final EventService eventService;

    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody EventCreateDto eventCreateDto) {
        return eventService.create(eventCreateDto);
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody EventUpdateDto eventUpdateDto) {
        return eventService.update(eventUpdateDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id) {
        return eventService.deleteOne(id);
    }

    @PutMapping("/publish/{id}")
    public ResponseEntity publish(@PathVariable Long id) {
        return eventService.publish(id);
    }

    @PutMapping("/pause/{id}")
    public ResponseEntity pause(@PathVariable Long id) {
        return eventService.pause(id);
    }

    @PutMapping("/archive/{id}")
    public ResponseEntity archive(@PathVariable Long id) {
        return eventService.archive(id);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getDetail(@PathVariable Long id) {
        return eventService.getDetail(id);
    }

    @GetMapping("/list")
    public Page<EventResponseDto> listPublished(@RequestParam(defaultValue = "0") int page) {
        return eventService.listPublished(page);
    }

    @GetMapping("/my-events")
    public Page<EventResponseDto> listByOwner(@RequestParam(defaultValue = "0") int page) {
        return eventService.listByOwner(page);
    }

    @GetMapping("/search")
    public Page<EventResponseDto> search(@RequestParam(defaultValue = "") String q, @RequestParam(defaultValue = "0") int page) {
        return eventService.search(q, page);
    }

    @GetMapping("/control")
    public void control() {}
}
