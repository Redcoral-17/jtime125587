package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;
import static javafx.collections.FXCollections.observableList;

/**
 * Controller della view per l'aggiunta di una nuova task.
 * Gestisce l'interazione con i controlli FXML, la validazione dei dati
 * e la creazione dell'entità Task tramite il {@code Controller<Task>}.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class AddTask {
    /**
     * Controller generico per le operazioni CRUD su {@code Task}.
     */
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    /**
     * Controller generico per le operazioni CRUD su {@code Project}.
     */
    private final Controller<Project> projectController = new HibernateController<>(Project.class);
    /**
     * DialogPane principale della view.
     */
    @FXML
    private DialogPane addTask;
    /**
     * Campo di testo per il nome della task.
     */
    @FXML
    private TextField name;
    /**
     * ComboBox per la selezione del progetto.
     */
    @FXML
    private ComboBox<String> project;
    /**
     * DatePicker per la selezione della data della task.
     */
    @FXML
    private DatePicker date;
    /**
     * ComboBox per la selezione dell'orario di inizio della task.
     */
    @FXML
    private ComboBox<LocalTime> start;
    /**
     * ComboBox per la selezione dell'orario di fine della task.
     */
    @FXML
    private ComboBox<LocalTime> end;
    /**
     * Riferimento al pulsante OK del Dialog.
     */
    @FXML
    private ButtonType okButton;

    /**
     * Inizializza la view:
     * - Popola la ComboBox dei progetti con i nomi dei progetti attivi;
     * - Imposta la data corrente come valore di default;
     * - Configura le ComboBox degli orari;
     * - Associa un filtro all'evento del pulsante OK per validare i dati
     *   e creare la Task solo se la validazione ha successo.
     */
    public void initialize() {
        project.setItems(observableList(projectController.getAll().stream().filter(project ->
                project.getStatus() == Status.ACTIVE).map(Project::getName).toList()));
        date.setValue(LocalDate.now());
        Main.setComboBox(start);
        Main.setComboBox(end);
        Button button = (Button) addTask.lookupButton(okButton);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(check()) {  event.consume(); return; }
            Project selectedProject = projectController.getAll().stream().filter(project ->
                    project.getName().equals(this.project.getValue())).findFirst().orElse(null);
            taskController.add(new Task(name.getText(), date.getValue(), start.getValue(), end.getValue(), selectedProject));
        });
    }

    /**
     * Esegue la validazione dei campi della form.
     *
     * @return {@code true} se è stato riscontrato un errore e mostra l'errore
     * all'utente tramite {@code Main.showError}, {@code false} altrimenti.
     */
    private boolean check() {
        if(name.getText().isBlank()) { showError("Name cannot be empty"); return true; }
        if(date.getValue() == null) { showError("Date cannot be empty"); return true; }
        if(start.getValue() == null) { showError("Start time cannot be empty"); return true; }
        if(end.getValue() == null) { showError("End time cannot be empty"); return true; }
        if(start.getValue().isAfter(end.getValue())) { showError("Start time cannot be after end time"); return true; }
        if(checkFreeTime()) { showError("There is already a task scheduled in this time range"); return true; }
        return false;
    }

    /**
     * Verifica che non esistano task attive nella stessa data con un
     * intervallo orario che si sovrappone all'intervallo selezionato.
     *
     * @return {@code true} se esiste una sovrapposizione con una task attiva, {@code false} altrimenti
     */
    private boolean checkFreeTime() {
        return taskController.getAll().stream().filter(task ->
                task.getDate().equals(date.getValue())).anyMatch(task -> task.getStatus() == Status.ACTIVE
                && start.getValue().isBefore(task.getEndTime()) && task.getStartTime().isBefore(end.getValue()));
    }
}
