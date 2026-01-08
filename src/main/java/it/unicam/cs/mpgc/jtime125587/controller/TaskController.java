package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
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

    public Duration timeOf(@NonNull Task task) {
        return Duration.between(task.getStartTime(), task.getEndTime());
    }

    public String getProjOf(@NonNull Task task) {
        if(task.getProject() != null) return task.getProject().getName();
        return "No Project";
    }
}

