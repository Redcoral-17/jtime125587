package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalTime;

/**
 * Controller principale dell'applicazione JavaFX.
 * Gestisce il comportamento della vista principale, la navigazione tra tab
 * e l'apertura dei dialog. Sono presenti inoltre un metodo per la
 * visualizzazione di errori e uno per la popolazione di ComboBox con orari.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class Main {
    /**
     * Riferimento al contenitore dei tab nella UI.
     */
    @FXML
    private TabPane main;
    /**
     * Controller della vista "Calendar".
     */
    @FXML
    private Calendar moveToCController;
    /**
     * Controller della vista "Projects".
     */
    @FXML
    private Projects moveToPController;
    /**
     * Controller della vista "Reports".
     */
    @FXML
    private Reports moveToRController;

    /**
     * Metodo di inizializzazione chiamato automaticamente da JavaFX dopo il caricamento del file FXML.
     * Aggiunge un listener per il cambio di tab e invoca {@code refresh()} sul controller
     * corrispondente quando un tab viene selezionato.
     */
    public void initialize() {
        main.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if(newTab != null) {
                if(newTab.getText().equals("Calendar") && moveToCController != null) moveToCController.refresh();
                if(newTab.getText().equals("Projects") && moveToPController != null) moveToPController.refresh();
                if(newTab.getText().equals("Reports") && moveToRController != null) moveToRController.refresh();
            }
        });
    }

    /**
     * Apre un dialog caricando la UI dal file FXML specificato.
     *
     * @param fxmlFile percorso relativo del file FXML da caricare.
     * @param title titolo del dialog mostrato all'utente.
     * @param task task opzionale da passare al controller del dialog.
     * @throws IOException se il file FXML non può essere caricato.
     */
    public static void openDialog(String fxmlFile, String title, Task task) throws IOException {
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

    /**
     * Mostra un alert di tipo ERROR con il messaggio fornito.
     *
     * @param errorMsg messaggio di errore da visualizzare.
     */
    public static void showError(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.setTitle("Error");
        alert.showAndWait();
    }

    /**
     * Popola una ComboBox con intervalli di 15 minuti per tutte le ore della giornata.
     *
     * @param comboBox ComboBox\<LocalTime\> da popolare con gli orari.
     */
    public static void setComboBox(ComboBox<LocalTime> comboBox) {
        for(int hour = 0; hour < 24; hour++) {
            for(int min = 0; min < 60; min += 15) {
                comboBox.getItems().add(LocalTime.of(hour, min));
            }
        }
    }
}
