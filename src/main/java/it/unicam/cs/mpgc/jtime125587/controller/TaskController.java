package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;

import java.time.Duration;
import java.util.function.Consumer;

public class TaskController {
    @Getter
    private static final TaskController instance = new TaskController();

    public void add(@NonNull Task task) {
        HibernateUtil.runInTx((Consumer<Session>) session -> session.persist(task));
    }

    public void delete(@NonNull Task task) {
        HibernateUtil.runInTx((Consumer<Session>) session -> session.remove(task));
    }

    public static Duration timeOf(@NonNull Task task) {
        return Duration.between(task.getStartTime(), task.getEndTime());
    }
}

