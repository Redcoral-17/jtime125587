package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import lombok.Getter;

import java.util.List;

public class ReportController {
    @Getter
    private static final ReportController instance = new ReportController();

    public void add(Report report) { HibernateUtil.doInTx(session -> session.persist(report)); }

    public void delete(Report report) { HibernateUtil.doInTx(session -> session.remove(report)); }

    public List<Report> getAll() {
        return HibernateUtil.doInSess(session -> session.createQuery("from Report", Report.class).getResultList());
    }
}
