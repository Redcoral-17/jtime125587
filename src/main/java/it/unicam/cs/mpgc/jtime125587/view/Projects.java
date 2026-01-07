package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ProjectController;
import it.unicam.cs.mpgc.jtime125587.controller.TaskController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

import static javafx.collections.FXCollections.observableArrayList;

public class Projects {
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
        refresh();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        start.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(TaskController.getInstance().getOldestDateTaskOf(cellData.getValue())));
        end.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(TaskController.getInstance().getLatestDateTaskOf(cellData.getValue())));
        time.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(ProjectController.getInstance().timeOf(cellData.getValue())));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void refresh() {
        projectList.setItems(observableArrayList(ProjectController.getInstance().getAll()));
    }

    @FXML
    private void openAddProject() throws IOException {
        Main.openDialog("/it/unicam/cs/mpgc/jtime125587/AddProject.fxml", "Add Project");
        refresh();
    }

    @FXML
    private void endProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        project.setStatus(Status.COMPLETED);
        ProjectController.getInstance().update(project);
    }

    @FXML
    private void deleteProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(ProjectController.getInstance().statusOf(project)) ProjectController.getInstance().delete(project);
        refresh();
    }
}
