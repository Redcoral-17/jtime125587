package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.AbstractTask;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

public class TaskController {
    @Getter
    private static final TaskController instance = new TaskController(AbstractTask.class);

    private TaskController(Class<AbstractTask> entityClass) { super(entityClass); }

    public List<AbstractTask> getTasksOf(@NonNull LocalDate date) {
        return getAll().stream().filter(task -> task.getDate().equals(date))
                .sorted(Comparator.comparing(AbstractTask::getStartTime)).toList();
    }

    public String getProjOf(@NonNull AbstractTask task) {
        if(task.getProject() != null) return task.getProject().getName();
        return "-No Project-";
    }

    public boolean checkFreeTime(Status status, @NonNull LocalTime start, @NonNull LocalTime end, @NonNull LocalDate date) {
        return getTasksOf(date).stream().anyMatch(task -> task.getStatus() == status
                && start.isBefore(task.getEndTime())
                && task.getStartTime().isBefore(end));
    }
}

