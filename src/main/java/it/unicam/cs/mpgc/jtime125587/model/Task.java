package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Status status;

    public Task(String name, Project project, LocalDate date, LocalTime start, LocalTime end) {
        this.name = name;
        this.project = project;
        this.date = date;
        this.startTime = start;
        this.endTime = end;
        this.status = Status.ACTIVE;
    }

}