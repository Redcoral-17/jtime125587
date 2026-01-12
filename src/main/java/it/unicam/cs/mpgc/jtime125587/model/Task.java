package it.unicam.cs.mpgc.jtime125587.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class Task extends AbstractTask {
    public Task(String name, Project project, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.project = project;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.oldDuration = Duration.ZERO;
        this.duration = Duration.between(startTime, endTime);
        this.status = Status.ACTIVE;
    }
}
