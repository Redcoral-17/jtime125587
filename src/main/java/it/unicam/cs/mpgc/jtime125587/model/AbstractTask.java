package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table
public abstract class AbstractTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    @ManyToOne
    @JoinColumn(name = "project_id")
    protected Project project;
    protected LocalDate date;
    protected LocalTime startTime;
    protected LocalTime endTime;
    protected Duration oldDuration;
    protected Duration duration;
    protected Status status;

}