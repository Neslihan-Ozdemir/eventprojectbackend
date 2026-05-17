package com.example.eventproject.dto;

import com.example.eventproject.util.EventStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventResponseDto {

    Long id;
    String title;
    LocalDate date;
    LocalTime time;
    String location;
    String category;
    EventStatus status;
    Long ownerId;
    String ownerName;

}
