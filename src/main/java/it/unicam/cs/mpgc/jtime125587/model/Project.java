package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Project {
    @Id
    private Long id;
    private String name;
    private String tag;
    private List<Task> tasks;
}
