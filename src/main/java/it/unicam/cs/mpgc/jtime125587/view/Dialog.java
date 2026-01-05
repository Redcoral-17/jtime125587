package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public abstract class Dialog {
    @FXML
    public void openDialog(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Dialog.class.getResource(fxmlFile));
        javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle(title);
        dialog.setDialogPane(loader.load());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Object fxmlCtrl = loader.getController();
            if (fxmlCtrl instanceof DialogResult) {
                Controller.getInstance().add(((DialogResult<?>) fxmlCtrl).getResult());
            } else {
                // fallback: se il controller FXML non espone un risultato, prova a passarlo direttamente
                Controller.getInstance().add(fxmlCtrl);
            }
        }
    }
}
