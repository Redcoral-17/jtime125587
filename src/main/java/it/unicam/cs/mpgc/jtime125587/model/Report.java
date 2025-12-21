package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Report {
    @Id
    private Long id;
    private String name;
    private String project;
    private String tag;
    private LocalDate start;
    private LocalDate end;
}
