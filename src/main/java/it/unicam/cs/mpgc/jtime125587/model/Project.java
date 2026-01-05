package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Status status;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
}
