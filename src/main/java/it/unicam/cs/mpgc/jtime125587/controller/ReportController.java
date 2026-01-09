package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Report;
import lombok.Getter;

import java.util.List;

public class ReportController extends AbstractController<Report> {
    @Getter
    private static final ReportController instance = new ReportController(Report.class);

    private ReportController(Class<Report> entityClass) { super(entityClass); }

    public List<String> getAllRepoNames() {
        return getAll().stream().map(Report::getName).toList();
    }
}
