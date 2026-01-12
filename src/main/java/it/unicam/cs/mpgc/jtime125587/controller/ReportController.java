package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Report;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.AbstractTask;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class ReportController {
    @Getter
    private static final ReportController instance = new ReportController(Report.class);

    private ReportController(Class<Report> entityClass) { super(entityClass); }

    public List<AbstractTask> getTasksOf(Report report) {
        if(report == null) return List.of();
        List<AbstractTask> tasks = TaskController.getInstance().getAll();
        if(report.getProject() != null) {
            tasks.removeIf(task -> !TaskController.getInstance().getProjOf(task).equals(report.getProject()));
        }
        if(report.getStartDate() != null && report.getEndDate() != null) {
            tasks.removeIf(task -> task.getDate().isBefore(report.getStartDate()) || task.getDate().isAfter(report.getEndDate()));
        }
        return tasks;
    }

    public String tasksActive(@NonNull List<AbstractTask> tasks) {
        return String.valueOf(tasks.stream().filter(task -> task.getStatus() == Status.ACTIVE).count());
    }

    public String tasksCompleted(@NonNull List<AbstractTask> tasks) {
        return String.valueOf(tasks.stream().filter(task -> task.getStatus() == Status.COMPLETED).count());
    }

    public String getDifDurOf(@NonNull AbstractTask task) {
        if(task.getOldDuration() != null) return task.getDuration().minus(task.getOldDuration()).toMinutes() + " m";
        return "-Not available-";
    }
}
