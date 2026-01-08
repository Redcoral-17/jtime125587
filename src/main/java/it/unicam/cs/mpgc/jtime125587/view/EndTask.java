package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.TaskController;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.NonNull;

import java.time.LocalTime;

public class EndTask {
    private Task task;
    @FXML
    private DialogPane endTask;
    @FXML
    private  Label name;
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
        button.setOnAction(event -> {
            if(start.getValue() != null) task.setStartTime(start.getValue());
            if(end.getValue() != null) task.setEndTime(end.getValue());
            task.setStatus(Status.COMPLETED);
            TaskController.getInstance().update(task);
        });
        if(task != null) setTask(task);
    }

    public void setTask(@NonNull Task task) {
        this.task = task;
        name.setText(task.getName());
        project.setText(TaskController.getInstance().getProjOf(task));
        date.setText(task.getDate().toString());
        start.setValue(task.getStartTime());
        end.setValue(task.getEndTime());
    }
}
