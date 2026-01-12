package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.AbstractTask;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjectController {
    @Getter
    private static final ProjectController instance = new ProjectController(Project.class);

    private ProjectController(Class<Project> entityClass) { super(entityClass); }

    public List<String> getActiveProjNames() {
        return getAll().stream().filter(project -> project.getStatus() == Status.ACTIVE).map(Project::getName).toList();
    }

    public boolean statusOf(@NonNull Project project) {
        return getTasksOf(project).stream().allMatch(task -> task.getStatus() == Status.COMPLETED);
    }

    public String getOldestDateTaskOf(@NonNull Project project) {
        LocalDate date = getTasksOf(project).stream().map(AbstractTask::getDate).min(LocalDate::compareTo).orElse(null);
        if(date != null) return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "-Not available-";
    }

    public String getLatestDateTaskOf(@NonNull Project project) {
        LocalDate date = getTasksOf(project).stream().map(AbstractTask::getDate).max(LocalDate::compareTo).orElse(null);
        if(date != null) return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "-Not available-";
    }

    public String durationOf(@NonNull Project project) {
        Duration duration = getTasksOf(project).stream().map(AbstractTask::getDuration).reduce(Duration.ZERO, Duration::plus);
        if(duration != null) return duration.toHours() + " h " + (duration.toMinutesPart()) + " m";
        return "-Not available-";
    }
}
