package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ProjectController;
import it.unicam.cs.mpgc.jtime125587.controller.TaskController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static javafx.collections.FXCollections.observableList;

public class AddTask {
    @FXML
    private DialogPane addTask;
    @FXML
    private  TextField name;
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
        project.setItems(observableList(ProjectController.getInstance().getAllProjNames()));
        date.setValue(LocalDate.now());
        Main.setComboBox(start);
        Main.setComboBox(end);
        Button button = (Button) addTask.lookupButton(okButton);
        button.setOnAction(event -> {
            Project p = ProjectController.getInstance().getByName(project.getValue());
            TaskController.getInstance().add(new Task(name.getText(), p, date.getValue(), start.getValue(), end.getValue()));
        });
    }
}
