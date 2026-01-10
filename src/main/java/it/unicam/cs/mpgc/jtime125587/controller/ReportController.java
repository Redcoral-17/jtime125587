package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Report;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class ReportController extends AbstractController<Report> {
    @Getter
    private static final ReportController instance = new ReportController(Report.class);

    private ReportController(Class<Report> entityClass) { super(entityClass); }

    public List<String> getAllRepoNames() {
        return getAll().stream().map(Report::getName).toList();
    }

    public Report getByName(String name) {
        return getAll().stream().filter(report -> report.getName().equals(name)).findFirst().orElse(null);
    }

    public List<Task> getTasksOf(@NonNull Report report) {
        return HibernateUtil.doInSess(session -> session.createQuery("from Task where project = :project " +
                        "or date >= :startDate and date <= :endDate", Task.class)
                .setParameter("project", report.getProject())
                .setParameter("startDate", report.getStartDate())
                .setParameter("endDate", report.getEndDate())
                .getResultList());
    }

    public String tasksActive(@NonNull List<Task> tasks) {
        return String.valueOf(tasks.stream().filter(task -> task.getStatus() == Status.ACTIVE).count());
    }

    public String tasksCompleted(@NonNull List<Task> tasks) {
        return String.valueOf(tasks.stream().filter(task -> task.getStatus() == Status.COMPLETED).count());
    }
}
