package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String project;
    private Set<AbstractTask> tasks;
    private LocalDate startDate;
    private LocalDate endDate;

    public Report(String name, String project, Set<AbstractTask> tasks, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.project = project;
        this.tasks = tasks;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
