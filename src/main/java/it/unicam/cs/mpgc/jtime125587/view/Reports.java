package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ReportController;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;

import java.io.IOException;

import static javafx.collections.FXCollections.observableList;

public class Reports {
    @FXML
    private ComboBox<Report> reportList;
    @FXML
    private PieChart selectedReport;

    public void initialize() {
        reportList.setItems(observableList(ReportController.getInstance().getAll()));
    }

    @FXML
    private void openAddReport() throws IOException {
        Main.openDialog("/it/unicam/cs/mpgc/jtime125587/AddReport.fxml", "Add Report");
    }

    @FXML
    private void deleteReport() {
        Report report = reportList.getSelectionModel().getSelectedItem();
        ReportController.getInstance().delete(report);
    }
}
