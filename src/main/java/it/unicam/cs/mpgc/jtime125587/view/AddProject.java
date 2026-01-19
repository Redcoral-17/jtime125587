package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;

/**
 * Controller della view per l'aggiunta di un nuovo progetto.
 * Collega i componenti FXML (dialog, campo nome e button type) e utilizza
 * un {@link HibernateController} per persistente il nuovo progetto.
 */
public class AddProject {
    /**
     * Controller generico per le operazioni CRUD su {@code Project}.
     */
    private final Controller<Project> controller = new HibernateController<>(Project.class);
    /**
     * DialogPane per l'aggiunta di un nuovo progetto.
     */
    @FXML
    private DialogPane addProject;
    /**
     * Campo di testo per il nome del progetto.
     */
    @FXML
    private TextField name;
    /**
     * Rappresenta il ButtonType del pulsante OK del dialog.
     */
    @FXML
    private ButtonType okButton;

    /**
     * Metodo di inizializzazione.
     * Recupera il pulsante di conferma dal DialogPane e aggiunge un filtro
     * sull'evento di azione per:
     * - Validare che il campo nome non sia vuoto;
     * - Mostrare un errore se la validazione fallisce;
     * - Altrimenti creare e aggiungere un nuovo progetto.
     */
    public void initialize() {
        Button confirm = (Button) addProject.lookupButton(okButton);
        confirm.addEventFilter(ActionEvent.ACTION, event -> {
            if(name.getText().isBlank()) { showError("Name cannot be empty"); event.consume(); return; }
            controller.add(new Project(name.getText()));
        });
    }
}
