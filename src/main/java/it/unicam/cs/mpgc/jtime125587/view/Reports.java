package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static it.unicam.cs.mpgc.jtime125587.view.Main.openDialog;
import static javafx.collections.FXCollections.observableList;

public class Reports {
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    private final Controller<Report> reportController = new HibernateController<>(Report.class);
    @FXML
    private ComboBox<String> reportList;
    @FXML
    private Label project;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label tasksStatus;
    @FXML
    private TableView<Task> reportTable;
    @FXML
    private TableColumn<Task, String> name;
    @FXML
    private TableColumn<Task, String> date;
    @FXML
    private TableColumn<Task, Status> status;
    @FXML
    private TableColumn<Task, String> duration;

    public void initialize() {
        reportList.valueProperty().addListener((obs, oldReport, newReport) -> {
            resetReportTable();
            if(newReport != null) setReportTable();
        });
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        duration.setCellValueFactory(cellData -> {
            if(cellData.getValue().getOldDuration() != null) {
                return new SimpleObjectProperty<>(cellData.getValue().getDuration().minus(cellData.getValue().getOldDuration()).toMinutes() + " m");
            }
            return new SimpleObjectProperty<>("-Not available-");
        });
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        refresh();
    }

    public void refresh() {
        reportList.setItems(observableList(reportController.getAll().stream().map(Report::getName).toList()));
    }

    private Stream<Task> getTasksOf(Report report) {
        if(report == null) return Stream.empty();
        return taskController.getAll().stream().filter(task -> {
               if(report.getProject() != null) {
                   if(task.getProject() != null) return task.getProject().getName().equals(report.getProject());
               }
               if(report.getStartDate() != null && report.getEndDate() != null) {
                   return !task.getDate().isBefore(report.getStartDate()) && !task.getDate().isAfter(report.getEndDate());
               }
               return false;
        });
    }

    @FXML
    private void setReportTable() {
        Report selectedReport = reportController.getAll().stream().filter(report ->
                report.getName().equals(this.reportList.getValue())).findFirst().orElse(null);
        if(selectedReport == null) return;
        reportTable.setItems(observableList(getTasksOf(selectedReport).toList()));
        if(selectedReport.getProject() != null) {
            project.setText(selectedReport.getProject());
        }
        if(selectedReport.getStartDate() != null && selectedReport.getEndDate() != null) {
            startDate.setText(selectedReport.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            endDate.setText(selectedReport.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        tasksStatus.setText(getTasksOf(selectedReport).filter(task -> task.getStatus() == Status.ACTIVE).count() +
                " / " + getTasksOf(selectedReport).filter(task -> task.getStatus() == Status.COMPLETED).count());
    }

    @FXML
    private void resetReportTable() {
        reportTable.setItems(observableList(List.of()));
        String s = "-Not available-";
        project.setText(s);
        startDate.setText(s);
        endDate.setText(s);
        tasksStatus.setText(s);
    }

    @FXML
    private void openAddReport() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddReport.fxml", "Add Report", null);
        refresh();
    }

    @FXML
    private void deleteReport() {
        Report selectedReport = reportController.getAll().stream().filter(report ->
                report.getName().equals(this.reportList.getValue())).findFirst().orElse(null);
        if(selectedReport != null) {
            reportController.delete(selectedReport);
            resetReportTable();
            refresh();
        }
    }
}
