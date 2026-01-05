package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;


import java.time.Duration;
import java.time.LocalDate;

public class ProjectController implements Controller<Project> {
    @Getter
    private static final ProjectController instance = new ProjectController();

    @Override
    public void add(@NonNull Project project) {
        HibernateUtil.runInTransaction(session -> session.persist(project));
    }

    @Override
    public void delete(@NonNull Project project) {
        HibernateUtil.runInTransaction(session -> session.remove(project));
    }

    private LocalDate dateOf(@NonNull Task task) {
        return task.getDate();
    }

    private Duration timeOf(@NonNull Project p) {
        Duration tot = Duration.ZERO;
        for(Task t : p.getTasks()) {
            tot = tot.plus(t.getEffTime());
        }
        return tot;
    }
}
