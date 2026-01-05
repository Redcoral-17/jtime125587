package it.unicam.cs.mpgc.jtime125587.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;

import java.io.IOException;

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

    @FXML
    public static void openDialog(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle(title);
        dialog.setDialogPane(loader.load());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.showAndWait();
    }
}
