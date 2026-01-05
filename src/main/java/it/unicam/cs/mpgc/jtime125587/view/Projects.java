package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

public class Projects extends Dialog {
    @FXML
    private TableView<Project> projectList;
    @FXML
    private TableColumn<Project, String> name;
    @FXML
    private TableColumn<Project, LocalDate> start;
    @FXML
    private TableColumn<Project, LocalDate> end;
    @FXML
    private TableColumn<Project, Duration> time;
    @FXML
    private TableColumn<Project, Status> status;

    public void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    private void openAddProject() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddProject.fxml", "Add Project");
    }
}
