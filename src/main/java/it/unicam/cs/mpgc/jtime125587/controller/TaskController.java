package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;

public class TaskController {
    @Getter
    private static final TaskController instance = new TaskController();

    public void add(Task task) {
        HibernateUtil.doInTx(session -> session.persist(task));
    }

    public void update(Task task) {
        HibernateUtil.doInTx(session -> session.merge(task));
    }

    public void delete(Task task) {
        HibernateUtil.doInTx(session -> session.remove(task));
    }

    public static Duration timeOf(@NonNull Task task) {
        return Duration.between(task.getStartTime(), task.getEndTime());
    }
}

