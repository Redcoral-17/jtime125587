package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

public class TaskController extends AbstractController<Task> {
    @Getter
    private static final TaskController instance = new TaskController(Task.class);

    private TaskController(Class<Task> entityClass) { super(entityClass); }

    public List<Task> getTasksOf(@NonNull LocalDate date) {
        return getAll().stream().filter(task -> task.getDate().equals(date))
                .sorted(Comparator.comparing(Task::getStartTime)).toList();
    }

    public String getProjOf(@NonNull Task task) {
        if(task.getProject() != null) return task.getProject().getName();
        return "-No Project-";
    }

    public boolean checkFreeTime(Status status, @NonNull LocalTime start, @NonNull LocalTime end, @NonNull LocalDate date) {
        return getTasksOf(date).stream().anyMatch(task -> task.getStatus() == status
                && start.isBefore(task.getEndTime())
                && task.getStartTime().isBefore(end));
    }
}

