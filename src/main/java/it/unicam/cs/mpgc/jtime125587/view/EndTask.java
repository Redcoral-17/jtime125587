package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;

public class EndTask {
    private Task task;
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    @FXML
    private DialogPane endTask;
    @FXML
    private Label name;
    @FXML
    private Label project;
    @FXML
    private Label date;
    @FXML
    private ComboBox<LocalTime> start;
    @FXML
    private ComboBox<LocalTime> end;
    @FXML
    private ButtonType okButton;

    public void initialize() {
        Main.setComboBox(start);
        Main.setComboBox(end);
        Button button = (Button) endTask.lookupButton(okButton);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(check()) { event.consume(); return; }
            taskController.update(taskUpdated());
        });
        if(task != null) setTask(task);
    }

    public void setTask(@NonNull Task task) {
        this.task = task;
        name.setText(task.getName());
        project.setText(task.getProject() != null ? task.getProject().getName() : "-No Project-");
        date.setText(task.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        start.setValue(task.getStartTime());
        end.setValue(task.getEndTime());
    }

    private Task taskUpdated() {
        task.setOldDuration(task.getDuration());
        task.setStartTime(start.getValue());
        task.setEndTime(end.getValue());
        task.setDuration(Duration.between(start.getValue(), end.getValue()));
        task.setStatus(Status.COMPLETED);
        return task;
    }

    private boolean check() {
        if(start.getValue().isAfter(end.getValue())) { showError("Start time cannot be after end time"); return true; }
        if(checkFreeTime()) { showError("There is already a task scheduled in this time range"); return true; }
        return false;
    }

    private boolean checkFreeTime() {
        return taskController.getAll().stream().filter(task ->
                task.getDate().equals(this.task.getDate())).anyMatch(task -> task.getStatus() == Status.COMPLETED
                && start.getValue().isBefore(task.getEndTime()) && task.getStartTime().isBefore(end.getValue()));
    }
}
