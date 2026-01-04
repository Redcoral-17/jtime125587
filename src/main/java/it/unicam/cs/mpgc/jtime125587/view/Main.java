package it.unicam.cs.mpgc.jtime125587.view;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class Main {
    @FXML
    private TabPane main;
    @FXML
    private Calendar moveToCalendar;
    @FXML
    private Projects moveToProjects;
    @FXML
    private Reports moveToReports;

    public void initialize() {
        main.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null) {
                if (newTab.getText().equals("Calendar") && moveToCalendar != null) moveToCalendar.initialize();
                if (newTab.getText().equals("Projects") && moveToProjects != null) moveToProjects.initialize();
                if (newTab.getText().equals("Reports") && moveToReports != null) moveToReports.initialize();
            }
        });
    }
}
