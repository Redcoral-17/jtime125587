package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ProjectController;
import it.unicam.cs.mpgc.jtime125587.controller.TaskController;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddTask {
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

    public void initialize() {
        date.setValue(LocalDate.now());
        project.getItems().setAll(ProjectController.getInstance().getProjectNames());
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 15) {
                start.getItems().add(LocalTime.of(hour, min));
                end.getItems().add(LocalTime.of(hour, min));
            }
        }
    }

    @FXML
    public void onConfirm() {
        TaskController.getInstance().add(new Task(
                name.getText(),
                ProjectController.getInstance().getProjectByName(project.getValue()),
                date.getValue(),
                start.getValue(),
                end.getValue(),
                Status.ACTIVE
        ));
    }
}
