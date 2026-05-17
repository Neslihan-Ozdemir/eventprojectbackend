package com.example.eventproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventSearchDto {

    @NotNull
    @NotEmpty
    String q;

    @NotNull
    @Min(0)
    Integer page;
}
