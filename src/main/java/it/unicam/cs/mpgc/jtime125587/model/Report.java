package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER)
    private List<Task> tasks;
    private LocalDate startDate;
    private LocalDate endDate;

    public Report(String name, Project project, List<Task> tasks, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.project = project;
        this.tasks = tasks;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
