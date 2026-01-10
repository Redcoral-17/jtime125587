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

    public List<String> getAllRepoNames() { return getAll().stream().map(Report::getName).toList(); }

    public Report getByName(String name) {
        return getAll().stream().filter(report -> report.getName().equals(name)).findFirst().orElse(null);
    }

    public List<Task> getTasksOf(Report report) {
        if(report == null) return List.of();
        List<Task> tasks = TaskController.getInstance().getAll();
        if(report.getProject() != null) {
            tasks.removeIf(task -> !TaskController.getInstance().getProjOf(task).equals(report.getProject()));
        }
        if(report.getStartDate() != null && report.getEndDate() != null) {
            tasks.removeIf(task -> task.getDate().isBefore(report.getStartDate()) || task.getDate().isAfter(report.getEndDate()));
        }
        return tasks;
    }

    public String tasksActive(@NonNull List<Task> tasks) {
        return String.valueOf(tasks.stream().filter(task -> task.getStatus() == Status.ACTIVE).count());
    }

    public String tasksCompleted(@NonNull List<Task> tasks) {
        return String.valueOf(tasks.stream().filter(task -> task.getStatus() == Status.COMPLETED).count());
    }

    public String getDifDurOf(@NonNull Task task) {
        if(task.getOldDuration() != null) return task.getDuration().minus(task.getOldDuration()).toMinutes() + " m";
        return "-Not available-";
    }
}
