package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import lombok.Getter;
import lombok.NonNull;

public class ReportController implements Controller<Report> {
    @Getter
    private static final ReportController instance = new ReportController();

    @Override
    public void add(@NonNull Report report) {
        HibernateUtil.runInTransaction(session -> session.persist(report));
    }

    @Override
    public void delete(@NonNull Report report) {
        HibernateUtil.runInTransaction(session -> session.remove(report));
    }
}
