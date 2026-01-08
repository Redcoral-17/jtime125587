package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ReportController;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;

import java.io.IOException;

import static it.unicam.cs.mpgc.jtime125587.view.Main.openDialog;
import static javafx.collections.FXCollections.observableList;

public class Reports {
    @FXML
    private ComboBox<Report> reportList;
    @FXML
    private PieChart selectedReport;

    public void initialize() {
        refresh();
    }

    public void refresh() {
        reportList.setItems(observableList(ReportController.getInstance().getAll()));
    }

    @FXML
    private void openAddReport() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddReport.fxml", "Add Report", null);
        refresh();
    }

    @FXML
    private void deleteReport() {
        ReportController.getInstance().delete(reportList.getSelectionModel().getSelectedItem());
        refresh();
    }
}
