package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ProjectController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

import static it.unicam.cs.mpgc.jtime125587.view.Main.openDialog;
import static javafx.collections.FXCollections.observableArrayList;

public class Projects {
    @FXML
    private TableView<Project> projectList;
    @FXML
    private TableColumn<Project, String> name;
    @FXML
    private TableColumn<Project, String> start;
    @FXML
    private TableColumn<Project, String> end;
    @FXML
    private TableColumn<Project, String> time;
    @FXML
    private TableColumn<Project, Status> status;

    public void initialize() {
        refresh();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        start.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(ProjectController.getInstance().getOldestDateTaskOf(cellData.getValue())));
        end.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(ProjectController.getInstance().getLatestDateTaskOf(cellData.getValue())));
        time.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(ProjectController.getInstance().timeOf(cellData.getValue())));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void refresh() {
        projectList.setItems(observableArrayList(ProjectController.getInstance().getAll()));
    }

    @FXML
    private void openAddProject() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddProject.fxml", "Add Project", null);
        refresh();
    }

    @FXML
    private void endProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(project != null && ProjectController.getInstance().statusOf(project)) {
            project.setStatus(Status.COMPLETED);
            ProjectController.getInstance().update(project);
            refresh();
        }
    }

    @FXML
    private void deleteProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(project != null && ProjectController.getInstance().getTasksOf(project).isEmpty()) {
            ProjectController.getInstance().delete(project);
            refresh();
        }
    }
}
