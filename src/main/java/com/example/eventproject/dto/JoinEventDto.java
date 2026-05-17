package com.example.eventproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinEventDto {

    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE)
    Long eventId;
}
