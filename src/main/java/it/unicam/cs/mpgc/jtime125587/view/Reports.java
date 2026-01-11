package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ReportController;
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

import static it.unicam.cs.mpgc.jtime125587.view.Main.openDialog;
import static javafx.collections.FXCollections.observableList;

public class Reports {
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
        refresh();
        reportList.valueProperty().addListener((obs, oldReport, newReport) -> {
            resetReportTable();
            setReportTable();
        });
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        duration.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(ReportController.getInstance().getDifDurOf(cellData.getValue())));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void refresh() {
        reportList.setItems(observableList(ReportController.getInstance().getAllRepoNames()));
    }

    @FXML
    private void setReportTable() {
        Report report = ReportController.getInstance().getByName(reportList.getValue());
        if(report == null) return;
        reportTable.setItems(observableList(ReportController.getInstance().getTasksOf(report)));
        if(report.getProject() != null) project.setText(report.getProject());
        if(report.getStartDate() != null && report.getEndDate() != null) {
            startDate.setText(report.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            endDate.setText(report.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        tasksStatus.setText(ReportController.getInstance().tasksActive(ReportController.getInstance().getTasksOf(report)) +
                " / " + ReportController.getInstance().tasksCompleted(ReportController.getInstance().getTasksOf(report)));
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
        Report report = ReportController.getInstance().getByName(reportList.getValue());
        ReportController.getInstance().delete(report);
        resetReportTable();
        refresh();
    }
}
