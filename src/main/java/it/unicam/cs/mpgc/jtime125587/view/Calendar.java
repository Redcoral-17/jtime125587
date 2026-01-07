package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.TaskController;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        refresh();
        date.setValue(LocalDate.now());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        project.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProject().getName()));
        start.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        end.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void refresh() {
        taskList.setItems(observableList(TaskController.getInstance().getAll()));
    }

    @FXML
    private void openAddTask() throws IOException {
        Main.openDialog("/it/unicam/cs/mpgc/jtime125587/AddTask.fxml", "Add Task");
        refresh();
    }

    @FXML
    private void openEndTask() throws IOException {
        Task task = taskList.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/it/unicam/cs/mpgc/jtime125587/EndTask.fxml"));
        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("End Task");
        dialog.setDialogPane(loader.load());
        EndTask controller = loader.getController();
        controller.setTask(task);
        dialog.showAndWait();
        refresh();
    }

    @FXML
    private void deleteTask() {
        Task task = taskList.getSelectionModel().getSelectedItem();
        TaskController.getInstance().delete(task);
        refresh();
    }
}
