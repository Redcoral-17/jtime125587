package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Task {
    @Id
    private Long id;
    private String name;
    private String project;
    private String tag;
    private LocalDate date;
    private LocalTime start;
    private Duration hpTime;
    private Duration effTime;
}