package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class TaskController {
    @Getter
    private static final TaskController instance = new TaskController();

    public void add(@NonNull Task task) { HibernateUtil.doInTx(session -> session.persist(task)); }

    public void update(@NonNull Task task) { HibernateUtil.doInTx(session -> session.merge(task)); }

    public void delete(@NonNull Task task) { HibernateUtil.doInTx(session -> session.remove(task)); }

    public List<Task> getAll() {
        return HibernateUtil.doInSess(session -> session.createQuery("from Task", Task.class).getResultList());
    }

    public List<Task> getAllOf(@NonNull Project project) {
        return HibernateUtil.doInSess(session -> session.createQuery("from Task where project = :project", Task.class)
                .setParameter("project", project)
                .getResultList());
    }

    public LocalDate getOldestDateTaskOf(@NonNull Project project) {
        return HibernateUtil.doInSess(session -> session.createQuery("select date from Task where project = :project order by date asc", LocalDate.class)
                .setParameter("project", project)
                .setMaxResults(1)
                .getSingleResultOrNull());
    }

    public LocalDate getLatestDateTaskOf(@NonNull Project project) {
        return HibernateUtil.doInSess(session -> session.createQuery("select date from Task where project = :project order by date desc", LocalDate.class)
                .setParameter("project", project)
                .setMaxResults(1)
                .getSingleResultOrNull());
    }

    public Duration timeOf(@NonNull Task task) {
        return Duration.between(task.getStartTime(), task.getEndTime());
    }
}

