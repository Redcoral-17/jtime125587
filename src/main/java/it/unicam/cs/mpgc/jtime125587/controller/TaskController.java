package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;

import java.time.Duration;
import java.time.LocalTime;

public class TaskController implements Controller<Task> {
    @Getter
    private static final TaskController instance = new TaskController();

    @Override
    public void add(@NonNull Session session, @NonNull Task task) {
        session.persist(task);
    }

    @Override
    public void delete(@NonNull Session session, @NonNull Task task) {
        session.remove(task);
    }

    private Duration leftTime(@NonNull Duration hpTime, @NonNull Duration effTime) {
        return hpTime.minus(effTime);
    }

    private LocalTime end(@NonNull LocalTime start, @NonNull Duration time) {
        return start.plusMinutes(time.toMinutes());
    }
}

