package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ReportController;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

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
    private TableView<Task> taskOfRepoList;
    @FXML
    private TableColumn<Task, String> date;
    @FXML
    private TableColumn<Task, Status> status;
    @FXML
    private TableColumn<Task, String> duration;

    public void initialize() {
        reportList.valueProperty().addListener((obs, oldReport, newReport) -> {
            Report report = ReportController.getInstance().getByName(newReport);
            taskOfRepoList.setItems(observableList(ReportController.getInstance().getTasksOf(report)));
            project.setText(report.getProject().getName());
            startDate.setText(report.getStartDate().toString());
            endDate.setText(report.getEndDate().toString());
            tasksStatus.setText(ReportController.getInstance().tasksActive(ReportController.getInstance().getTasksOf(report)) +
                    " / " + ReportController.getInstance().tasksCompleted(ReportController.getInstance().getTasksOf(report)));
        });
    }

    public void refresh() {
        reportList.setItems(observableList(ReportController.getInstance().getAllRepoNames()));
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
        refresh();
    }
}
