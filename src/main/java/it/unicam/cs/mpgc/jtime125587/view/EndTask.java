package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;

/**
 * Controller della dialog per la chiusura di una task.
 * Gestisce l'interazione con la UI, la validazione degli orari
 * e l'aggiornamento del task con l'orario effettivo.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class EndTask {
    /**
     * Task attualmente visualizzata nella dialog.
     */
    private Task task;
    /**
     * Controller generico per le operazioni CRUD su {@code Task}.
     */
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    /**
     * DialogPane principale associato al file FXML.
     */
    @FXML
    private DialogPane endTask;
    /**
     * Label che mostra il nome della task.
     */
    @FXML
    private Label name;
    /**
     * Label che mostra il nome del progetto associato (se presente).
     */
    @FXML
    private Label project;
    /**
     * Label che mostra la data della task.
     */
    @FXML
    private Label date;
    /**
     * ComboBox per selezionare l'orario di inizio.
     */
    @FXML
    private ComboBox<LocalTime> start;
    /**
     * ComboBox per selezionare l'orario di fine.
     */
    @FXML
    private ComboBox<LocalTime> end;
    /**
     * ButtonType corrispondente al pulsante OK nella dialog.
     */
    @FXML
    private ButtonType okButton;

    /**
     * Inizializza la dialog:
     * - Popola le ComboBox degli orari;
     * - Aggiunge un event filter al pulsante OK per validare i dati e aggiornare il task
     * - Se un task è già assegnato, ne imposta i valori nella UI
     */
    public void initialize() {
        Main.setComboBox(start);
        Main.setComboBox(end);
        Button button = (Button) endTask.lookupButton(okButton);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(check()) { event.consume(); return; }
            taskController.update(taskUpdated());
        });
        if(task != null) setTask(task);
    }

    /**
     * Imposta il task da visualizzare nella dialog e aggiorna i controlli UI.
     *
     * @param task il task da mostrare
     */
    public void setTask(@NonNull Task task) {
        this.task = task;
        name.setText(task.getName());
        project.setText(task.getProject() != null ? task.getProject().getName() : "-No Project-");
        date.setText(task.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        start.setValue(task.getStartTime());
        end.setValue(task.getEndTime());
    }

    /**
     * Restituisce il task aggiornato con i valori presi dalla UI.
     * Aggiorna la durata, l'orario di inizio/fine e lo status a COMPLETED,
     * salvando la durata precedente in {@code oldDuration}.
     *
     * @return il task aggiornato
     */
    private Task taskUpdated() {
        task.setOldDuration(task.getDuration());
        task.setStartTime(start.getValue());
        task.setEndTime(end.getValue());
        task.setDuration(Duration.between(start.getValue(), end.getValue()));
        task.setStatus(Status.COMPLETED);
        return task;
    }

    /**
     * Esegue i controlli di validità sui dati inseriti.
     *
     * @return {@code true} se è stato rilevato un errore e mostra un messaggio
     * tramite {@code Main.showError}, {@code false} altrimenti
     */
    private boolean check() {
        if(start.getValue().isAfter(end.getValue())) { showError("Start time cannot be after end time"); return true; }
        if(checkFreeTime()) { showError("There is already a task scheduled in this time range"); return true; }
        return false;
    }

    /**
     * Controlla che non esistano task già completati nella stessa data
     * che si sovrappongano all'intervallo selezionato.
     *
     * @return {@code true} se esiste un task in conflitto, {@code false} altrimenti
     */
    private boolean checkFreeTime() {
        return taskController.getAll().stream().filter(task ->
                task.getDate().equals(this.task.getDate())).anyMatch(task -> task.getStatus() == Status.COMPLETED
                && start.getValue().isBefore(task.getEndTime()) && task.getStartTime().isBefore(end.getValue()));
    }
}
