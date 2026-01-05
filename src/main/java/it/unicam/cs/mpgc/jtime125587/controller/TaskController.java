package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalTime;

public class TaskController implements Controller<Task> {
    @Getter
    private static final TaskController instance = new TaskController();

    @Override
    public void add(@NonNull Task task) {
        HibernateUtil.runInTransaction(session -> session.persist(task));
    }

    @Override
    public void delete(@NonNull Task task) {
        HibernateUtil.runInTransaction(session -> session.remove(task));
    }

    private Duration leftTime(@NonNull Duration hpTime, @NonNull Duration effTime) {
        return hpTime.minus(effTime);
    }

    private LocalTime end(@NonNull LocalTime start, @NonNull Duration time) {
        return start.plusMinutes(time.toMinutes());
    }
}

