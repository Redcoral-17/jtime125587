package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.TaskController;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Calendar {
    @FXML
    private DatePicker date;
    @FXML
    private TableView<Task> taskList;
    @FXML
    private TableColumn<Task, String> name;
    @FXML
    private TableColumn<Task, String> project;
    @FXML
    private TableColumn<Task, String> start;
    @FXML
    private TableColumn<Task, String> end;
    @FXML
    private TableColumn<Task, Status> status;

    public void initialize() {
        date.setValue(LocalDate.now());
//        taskList.setItems(observableArrayList(.getInstance().getAllMovUntilNow()));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        project.setCellValueFactory(new PropertyValueFactory<>("project"));
        start.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        end.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    private void openAddTask() throws IOException {
        Main.openDialog("/it/unicam/cs/mpgc/jtime125587/AddTask.fxml", "Add Task");
    }

    @FXML
    private void deleteTask() {
        Task task = taskList.getSelectionModel().getSelectedItem();
        TaskController.getInstance().delete(task);
    }
}
