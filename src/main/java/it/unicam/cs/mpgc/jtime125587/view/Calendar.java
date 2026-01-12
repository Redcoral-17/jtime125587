package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.TaskController;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.AbstractTask;
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
    private TableView<AbstractTask> taskList;
    @FXML
    private TableColumn<AbstractTask, String> name;
    @FXML
    private TableColumn<AbstractTask, String> project;
    @FXML
    private TableColumn<AbstractTask, String> start;
    @FXML
    private TableColumn<AbstractTask, String> end;
    @FXML
    private TableColumn<AbstractTask, Status> status;

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
        AbstractTask task = taskList.getSelectionModel().getSelectedItem();
        if(task != null && task.getStatus() == Status.ACTIVE) {
            openDialog("/it/unicam/cs/mpgc/jtime125587/EndTask.fxml", "End Task", task);
            refresh();
        }
    }

    @FXML
    private void deleteTask() {
        AbstractTask task = taskList.getSelectionModel().getSelectedItem();
        if(task != null && task.getStatus() == Status.ACTIVE) {
            TaskController.getInstance().delete(task);
            refresh();
        }
    }
}
