package com.example.eventproject.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventUpdateDto {

    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE)
    Long id;

    @NotNull
    @Size(min = 2, max = 150)
    @NotEmpty
    String title;

    @NotNull
    @FutureOrPresent
    LocalDate date;

    @NotNull
    LocalTime time;

    @NotNull
    @Size(min = 2, max = 200)
    @NotEmpty
    String location;

    @NotNull
    @Size(min = 2, max = 500)
    @NotEmpty
    String description;

    @NotNull
    @Size(min = 2, max = 100)
    @NotEmpty
    String category;
}
