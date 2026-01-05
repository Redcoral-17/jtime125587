package it.unicam.cs.mpgc.jtime125587.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class Dialog extends AddTask {
    @FXML
    public void openDialog(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Dialog.class.getResource(fxmlFile));
        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle(title);
        dialog.setDialogPane(loader.load());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(btn -> {
            if(btn == ButtonType.OK) onConfirm();
            return btn;
        });
        dialog.showAndWait();
    }
}

