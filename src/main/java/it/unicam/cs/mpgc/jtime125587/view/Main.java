package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.time.LocalTime;

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
                if (newTab.getText().equals("Calendar") && moveToCalendar != null) moveToCalendar.refresh();
                if (newTab.getText().equals("Projects") && moveToProjects != null) moveToProjects.refresh();
                if (newTab.getText().equals("Reports") && moveToReports != null) moveToReports.initialize();
            }
        });
    }

    public static void openDialog(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle(title);
        dialog.setDialogPane(loader.load());
        dialog.showAndWait();
    }

//    public static void showError() {
//        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
//        alert.setTitle("Error");
//        alert.setHeaderText("An error occurred");
//        alert.setContentText("Please try again.");
//        alert.showAndWait();
//    }

    public static void setComboBox(ComboBox<LocalTime> comboBox) {
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 15) {
                comboBox.getItems().add(LocalTime.of(hour, min));
            }
        }
    }
}
