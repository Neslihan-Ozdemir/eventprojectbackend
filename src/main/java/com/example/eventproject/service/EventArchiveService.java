package com.example.eventproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class EventArchiveService {

    final EventService eventService;

    @Scheduled(cron = "0 0 0 * * *")
    public void archiveExpiredEvents() {
        eventService.archiveExpiredEvents();
    }

    @PostConstruct
    public void archiveExpiredEventsOnStartup() {
        eventService.archiveExpiredEvents();
    }
}
