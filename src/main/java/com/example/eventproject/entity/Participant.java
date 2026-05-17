package com.example.eventproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Participant {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;
}