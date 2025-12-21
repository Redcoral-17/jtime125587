package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Report;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;

public class ReportController implements Controller<Report> {
    @Getter
    private static final ReportController instance = new ReportController();

    @Override
    public void add(@NonNull Session session, @NonNull Report report) {
        session.persist(report);
    }

    @Override
    public void delete(@NonNull Session session, @NonNull Report report) {
        session.remove(report);
    }
}
