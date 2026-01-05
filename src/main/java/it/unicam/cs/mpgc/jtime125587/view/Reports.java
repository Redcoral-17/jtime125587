package it.unicam.cs.mpgc.jtime125587.view;

import javafx.fxml.FXML;

import java.io.IOException;

public class Reports {
    public void initialize() {}

    @FXML
    private void openAddReport() throws IOException {
        Main.openDialog("/it/unicam/cs/mpgc/jtime125587/AddReport.fxml", "Add Report");
    }
}
