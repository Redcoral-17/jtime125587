package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import static it.unicam.cs.mpgc.jtime125587.view.Main.*;
import static javafx.collections.FXCollections.observableList;

/**
 * Controller della vista Calendar.
 * Gestisce l'interazione tra la UI (file FXML associato) e il modello {@code Task}.
 * Fornisce metodi per inizializzare i componenti, aggiornare la lista delle task,
 * aprire dialog per aggiungere o terminare task e cancellare task attive.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class Calendar {
    /**
     * Controller generico per le operazioni CRUD su {@code Task}.
     */
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    /**
     * Campo FXML per la selezione della data visualizzata nel calendario.
     */
    @FXML
    private DatePicker date;
    /**
     * Tabella FXML che mostra la lista delle task per la data selezionata.
     */
    @FXML
    private TableView<Task> taskList;
    /**
     * Colonna FXML per il nome della task.
     */
    @FXML
    private TableColumn<Task, String> name;
    /**
     * Colonna FXML per il nome del progetto associato alla task.
     */
    @FXML
    private TableColumn<Task, String> project;
    /**
     * Colonna FXML per l'orario di inizio della task.
     */
    @FXML
    private TableColumn<Task, String> start;
    /**
     * Colonna FXML per l'orario di fine della task.
     */
    @FXML
    private TableColumn<Task, String> end;
    /**
     * Colonna FXML per lo stato della task.
     */
    @FXML
    private TableColumn<Task, Status> status;

    /**
     * Inizializza i componenti della vista:
     * - Imposta la data corrente nel {@code DatePicker};
     * - Aggiunge un listener per aggiornare la lista quando la data cambia;
     * - Configura i cell value factory per le colonne della tabella;
     * - Ricarica la lista delle task correnti.
     */
    public void initialize() {
        date.setValue(LocalDate.now());
        date.valueProperty().addListener((obs, oldDate, newDate) -> refresh());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        project.setCellValueFactory(cellData -> {
            if(cellData.getValue().getProject() != null) {
                return new SimpleObjectProperty<>(cellData.getValue().getProject().getName());
            } return new SimpleObjectProperty<>("-No Project-");
        });
        start.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        end.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        refresh();
    }

    /**
     * Aggiorna gli elementi mostrati nella {@code TableView} filtrando tutte le task
     * per la data selezionata e ordinandole per orario di inizio.
     */
    public void refresh() {
        taskList.setItems(observableList(taskController.getAll().stream().filter(task ->
                task.getDate().equals(date.getValue())).sorted(Comparator.comparing(Task::getStartTime)).toList()));
    }

    /**
     * Apre il dialog per aggiungere una nuova task.
     *
     * @throws IOException se il caricamento della risorsa FXML fallisce.
     */
    @FXML
    private void openAddTask() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddTask.fxml", "Add Task", null);
        refresh();
    }

    /**
     * Apre il dialog per terminare la task selezionata.
     *
     * @throws IOException se il caricamento della risorsa FXML fallisce.
     */
    @FXML
    private void openEndTask() throws IOException {
        Task task = taskList.getSelectionModel().getSelectedItem();
        if(task != null && task.getStatus() == Status.ACTIVE) {
            openDialog("/it/unicam/cs/mpgc/jtime125587/EndTask.fxml", "End Task", task);
            refresh();
        }
    }

    /**
     * Elimina la task selezionata.
     */
    @FXML
    private void deleteTask() {
        Task task = taskList.getSelectionModel().getSelectedItem();
        if(task != null && task.getStatus() == Status.ACTIVE) {
            taskController.delete(task);
            refresh();
        }
    }
}
