package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;
import static javafx.collections.FXCollections.observableList;

public class AddTask {
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    private final Controller<Project> projectController = new HibernateController<>(Project.class);
    @FXML
    private DialogPane addTask;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> project;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<LocalTime> start;
    @FXML
    private ComboBox<LocalTime> end;
    @FXML
    private ButtonType okButton;

    public void initialize() {
        project.setItems(observableList(projectController.getAll().stream().filter(project ->
                project.getStatus() == Status.ACTIVE).map(Project::getName).toList()));
        date.setValue(LocalDate.now());
        Main.setComboBox(start);
        Main.setComboBox(end);
        Button button = (Button) addTask.lookupButton(okButton);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(check()) {  event.consume(); return; }
            Project selectedProject = projectController.getAll().stream().filter(project ->
                    project.getName().equals(this.project.getValue())).findFirst().orElse(null);
            taskController.add(new Task(name.getText(), date.getValue(), start.getValue(), end.getValue(), selectedProject));
        });
    }

    private boolean check() {
        if(name.getText().isBlank()) { showError("Name cannot be empty"); return true; }
        if(date.getValue() == null) { showError("Date cannot be empty"); return true; }
        if(start.getValue() == null) { showError("Start time cannot be empty"); return true; }
        if(end.getValue() == null) { showError("End time cannot be empty"); return true; }
        if(start.getValue().isAfter(end.getValue())) { showError("Start time cannot be after end time"); return true; }
        if(checkFreeTime()) { showError("There is already a task scheduled in this time range"); return true; }
        return false;
    }

    private boolean checkFreeTime() {
        return taskController.getAll().stream().filter(task ->
                task.getDate().equals(date.getValue())).anyMatch(task -> task.getStatus() == Status.ACTIVE
                && start.getValue().isBefore(task.getEndTime()) && task.getStartTime().isBefore(end.getValue()));
    }
}
