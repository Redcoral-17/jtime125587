package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private TableColumn<Task, LocalTime> start;
    @FXML
    private TableColumn<Task, Duration> time;
    @FXML
    private TableColumn<Task, Status> status;

    public void initialize() {
        date.setValue(LocalDate.now());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        project.setCellValueFactory(new PropertyValueFactory<>("project"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        time.setCellValueFactory(new PropertyValueFactory<>("effTime"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
