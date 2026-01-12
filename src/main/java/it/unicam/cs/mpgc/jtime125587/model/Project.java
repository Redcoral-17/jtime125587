package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Task> tasks;
    private Status status;

    public Project(String name) {
        this.name = name;
        this.tasks = List.of();
        this.status = Status.ACTIVE;
    }
}
