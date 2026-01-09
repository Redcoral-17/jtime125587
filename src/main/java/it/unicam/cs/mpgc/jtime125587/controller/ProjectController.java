package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjectController extends AbstractController<Project> {
    @Getter
    private static final ProjectController instance = new ProjectController(Project.class);

    private ProjectController(Class<Project> entityClass) { super(entityClass); }

    public List<String> getAllProjNames() {
        return getAll().stream().map(Project::getName).toList();
    }

    public List<String> getActiveProjNames() {
        return getAll().stream().filter(project -> project.getStatus() == Status.ACTIVE).map(Project::getName).toList();
    }

    public Project getByName(String name) {
        return getAll().stream().filter(project -> project.getName().equals(name)).findFirst().orElse(null);
    }

    public List<Task> getTasksOf(@NonNull Project project) {
        return HibernateUtil.doInSess(session -> session.createQuery("from Task where project = :project", Task.class)
                .setParameter("project", project)
                .getResultList());
    }

    public boolean statusOf(@NonNull Project project) {
        return getTasksOf(project)
                .stream()
                .allMatch(task -> task.getStatus() == Status.COMPLETED);
    }

    public String getOldestDateTaskOf(@NonNull Project project) {
        LocalDate date = getTasksOf(project)
                .stream()
                .map(Task::getDate)
                .min(LocalDate::compareTo)
                .orElse(null);
        if(date != null) return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "-Not available-";
    }

    public String getLatestDateTaskOf(@NonNull Project project) {
        LocalDate date = getTasksOf(project)
                .stream()
                .map(Task::getDate)
                .max(LocalDate::compareTo)
                .orElse(null);
        if(date != null) return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "-Not available-";
    }

    public String timeOf(@NonNull Project project) {
        Duration time = getTasksOf(project)
                .stream()
                .map(TaskController.getInstance()::timeOf)
                .reduce(Duration.ZERO, Duration::plus);
        if(time != null) return time.toHours() + " h " + (time.toMinutesPart()) + " m";
        return "Not available";
    }
}
