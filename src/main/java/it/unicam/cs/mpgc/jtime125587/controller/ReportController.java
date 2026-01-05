package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;

import java.util.function.Consumer;

public class ReportController {
    @Getter
    private static final ReportController instance = new ReportController();

    public void add(@NonNull Report report) {
        HibernateUtil.runInTransaction((Consumer<Session>) session -> session.persist(report));
    }

    public void delete(@NonNull Report report) {
        HibernateUtil.runInTransaction((Consumer<Session>) session -> session.remove(report));
    }
}
