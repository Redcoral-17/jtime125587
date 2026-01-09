package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ProjectController;
import it.unicam.cs.mpgc.jtime125587.controller.TaskController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;
import static javafx.collections.FXCollections.observableList;

public class AddTask {
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
        project.setItems(observableList(ProjectController.getInstance().getActiveProjNames()));
        date.setValue(LocalDate.now());
        Main.setComboBox(start);
        Main.setComboBox(end);
        Button button = (Button) addTask.lookupButton(okButton);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(check()) {  event.consume(); return; }
            Project p = ProjectController.getInstance().getByName(project.getValue());
            TaskController.getInstance().add(new Task(name.getText(), p, date.getValue(), start.getValue(), end.getValue()));
        });
    }

    private boolean check() {
        if(name.getText().isBlank()) { showError("Name cannot be empty"); return true; }
        if(date.getValue() == null) { showError("Date cannot be empty"); return true; }
        if(start.getValue() == null) { showError("Start time cannot be empty"); return true; }
        if(end.getValue() == null) { showError("End time cannot be empty"); return true; }
        if(start.getValue().isAfter(end.getValue())) { showError("Start time cannot be after end time"); return true; }
        if(TaskController.getInstance().checkFreeTime(start.getValue(), end.getValue(), date.getValue())) {
            showError("There is already a task scheduled in this time range");
            return true;
        }
        else { return false; }
    }
}
