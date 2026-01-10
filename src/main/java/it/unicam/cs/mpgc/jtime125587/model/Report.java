package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate startDate;
    private LocalDate endDate;

    public Report(String name, String project, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
