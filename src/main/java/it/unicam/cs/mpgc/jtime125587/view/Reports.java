package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static it.unicam.cs.mpgc.jtime125587.view.Main.openDialog;
import static javafx.collections.FXCollections.observableList;

public class Reports {
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
            setReportTable();
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

    @FXML
    private void setReportTable() {
        Report r = reportController.getAll().stream().filter(report ->
                Objects.equals(report.getName(), reportList.getValue())).findFirst().orElse(null);
        if(r == null) return;
        reportTable.setItems(observableList(r.getTasks()));
        if(r.getProject() != null) project.setText(r.getProject());
        if(r.getStartDate() != null && r.getEndDate() != null) {
            startDate.setText(r.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            endDate.setText(r.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        tasksStatus.setText(r.getTasks().stream().filter(task -> task.getStatus() == Status.ACTIVE).count() +
                " / " + r.getTasks().stream().filter(task -> task.getStatus() == Status.COMPLETED).count());
    }

    @FXML
    private void resetReportTable() {
        reportTable.getItems().clear();
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
        Report r = reportController.getAll().stream().filter(report ->
                report.getName().equals(this.reportList.getValue())).findFirst().orElse(null);
        reportController.delete(r);
        resetReportTable();
        refresh();
    }
}
