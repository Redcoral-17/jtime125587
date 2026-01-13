package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static it.unicam.cs.mpgc.jtime125587.view.Main.openDialog;
import static javafx.collections.FXCollections.observableArrayList;

public class Projects {
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    private final Controller<Project> projectController = new HibernateController<>(Project.class);
    @FXML
    private TableView<Project> projectList;
    @FXML
    private TableColumn<Project, String> name;
    @FXML
    private TableColumn<Project, String> start;
    @FXML
    private TableColumn<Project, String> end;
    @FXML
    private TableColumn<Project, String> duration;
    @FXML
    private TableColumn<Project, Status> status;

    public void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        start.setCellValueFactory(cellData -> {
            LocalDate date = getTasksOf(cellData.getValue()).map(Task::getDate).min(LocalDate::compareTo).orElse(null);
            if(date != null) return new SimpleObjectProperty<>(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return new SimpleObjectProperty<>("-Not available-");
        });
        end.setCellValueFactory(cellData -> {
            LocalDate date = getTasksOf(cellData.getValue()).map(Task::getDate).max(LocalDate::compareTo).orElse(null);
            if(date != null) return new SimpleObjectProperty<>(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return new SimpleObjectProperty<>("-Not available-");
        });
        duration.setCellValueFactory(cellData -> {
            Duration duration = getTasksOf(cellData.getValue()).map(Task::getDuration).reduce(Duration::plus).orElse(Duration.ZERO);
            if(duration != Duration.ZERO) return new SimpleObjectProperty<>(duration.toHours() + " h " + (duration.toMinutesPart()) + " m");
            return new SimpleObjectProperty<>("-Not available-");
        });
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        refresh();
    }

    public void refresh() {
        projectList.setItems(observableArrayList(projectController.getAll()));
    }

    private Stream<Task> getTasksOf(Project project) {
        return taskController.getAll().stream().filter(task -> {
            if(task.getProject() != null) return task.getProject().getId().equals(project.getId());
            return false;
        });
    }

    @FXML
    private void openAddProject() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddProject.fxml", "Add Project", null);
        refresh();
    }

    @FXML
    private void endProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(project != null && getTasksOf(project).allMatch(task -> task.getStatus() == Status.COMPLETED)) {
            project.setStatus(Status.COMPLETED);
            projectController.update(project);
            refresh();
        }
    }

    @FXML
    private void deleteProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(project != null && getTasksOf(project).findAny().isEmpty()) {
            projectController.delete(project);
            refresh();
        }
    }
}
