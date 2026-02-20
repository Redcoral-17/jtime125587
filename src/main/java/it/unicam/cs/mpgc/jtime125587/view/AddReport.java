package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;
import static javafx.collections.FXCollections.observableList;

/**
 * Controller della vista per l'aggiunta di un nuovo Report.
 * Si occupa di inizializzare i controlli FXML, validare i dati inseriti
 * e creare l'entità Report tramite il controller Hibernate.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class AddReport {
    /**
     * Controller generico per le operazioni CRUD su {@code Project}.
     */
    private final Controller<Project> projectController = new HibernateController<>(Project.class);
    /**
     * Controller generico per le operazioni CRUD su {@code Report}.
     */
    private final Controller<Report> reportController = new HibernateController<>(Report.class);
    /**
     * DialogPane radice della finestra di aggiunta.
     */
    @FXML
    private DialogPane addReport;
    /**
     * Campo testo per il nome del report.
     */
    @FXML
    private TextField name;
    /**
     * ComboBox per la selezione del progetto.
     */
    @FXML
    private ComboBox<String> project;
    /**
     * DatePicker per la selezione della data di inizio.
     */
    @FXML
    private DatePicker start;
    /**
     * DatePicker per la selezione della data di fine.
     */
    @FXML
    private DatePicker end;
    /**
     * ButtonType corrispondente al pulsante OK nella dialog.
     */
    @FXML
    private ButtonType okButton;

    /**
     * Metodo di inizializzazione che:
     * - Popola la ComboBox dei progetti con i nomi disponibili.
     * - Imposta date di default per start ed end.
     * - Aggiunge un filtro sull'evento del bottone OK per eseguire la validazione
     *   e creare il Report solo se i dati sono validi.
     */
    public void initialize() {
        project.setItems(observableList(projectController.getAll().stream().map(Project::getName).toList()));
        start.setValue(LocalDate.now());
        end.setValue(LocalDate.now().plusDays(7));
        Button button = (Button) addReport.lookupButton(okButton);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(check()) {  event.consume(); return; }
            reportController.add(new Report(name.getText(), start.getValue(), end.getValue(), project.getValue()));
        });
    }

    /**
     * Esegue la validazione dei campi del form.
     *
     * @return {@code true} se è stato riscontrato un errore e mostra l'errore
     * all'utente tramite {@code Main.showError}, {@code false} altrimenti.
     */
    private boolean check() {
        if(name.getText().isBlank()) { showError("Name cannot be empty"); return true; }
        if(start.getValue() != null && end.getValue() != null) {
            if(start.getValue().isAfter(end.getValue())) { showError("Start date cannot be after end date"); return true; }
        }
        return false;
    }
}
