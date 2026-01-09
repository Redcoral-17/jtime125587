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

import static it.unicam.cs.mpgc.jtime125587.view.Main.*;
import static javafx.collections.FXCollections.observableList;

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
        date.valueProperty().addListener((obs, oldDate, newDate) -> refresh());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        project.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(TaskController.getInstance().getProjOf(cellData.getValue())));
        start.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        end.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        refresh();
    }

    public void refresh() {
        taskList.setItems(observableList(TaskController.getInstance().getTasksOf(date.getValue())));
    }

    @FXML
    private void openAddTask() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddTask.fxml", "Add Task", null);
        refresh();
    }

    @FXML
    private void openEndTask() throws IOException {
        Task task = taskList.getSelectionModel().getSelectedItem();
        if(task != null && task.getStatus() != Status.COMPLETED) {
            openDialog("/it/unicam/cs/mpgc/jtime125587/EndTask.fxml", "End Task", task);
            refresh();
        }
    }

    @FXML
    private void deleteTask() {
        Task task = taskList.getSelectionModel().getSelectedItem();
        TaskController.getInstance().delete(task);
        refresh();
    }
}
