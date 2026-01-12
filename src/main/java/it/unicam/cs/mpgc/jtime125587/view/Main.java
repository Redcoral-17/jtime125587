package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.model.AbstractTask;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalTime;

public class Main {
    @FXML
    private TabPane main;
    @FXML
    private Calendar moveToCController;
    @FXML
    private Projects moveToPController;
    @FXML
    private Reports moveToRController;

    public void initialize() {
        main.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if(newTab != null) {
                if(newTab.getText().equals("Calendar") && moveToCController != null) moveToCController.refresh();
                if(newTab.getText().equals("Projects") && moveToPController != null) moveToPController.refresh();
                if(newTab.getText().equals("Reports") && moveToRController != null) moveToRController.refresh();
            }
        });
    }

    public static void openDialog(String fxmlFile, String title, AbstractTask task) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setDialogPane(loader.load());
        if(task != null) {
            EndTask controller = loader.getController();
            controller.setTask(task);
        }
        dialog.showAndWait();
    }

    public static void showError(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.setTitle("Error");
        alert.showAndWait();
    }

    public static void setComboBox(ComboBox<LocalTime> comboBox) {
        for(int hour = 0; hour < 24; hour++) {
            for(int min = 0; min < 60; min += 15) {
                comboBox.getItems().add(LocalTime.of(hour, min));
            }
        }
    }
}
