package com.example.eventproject.entity;

import com.example.eventproject.util.EventStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 120)
    private String title;

    private LocalDate date;

    private LocalTime time;

    @Column(length = 200)
    private String location;

    @Column(length = 500)
    private String description;

    @Column(length = 100)
    private String category;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;
}
