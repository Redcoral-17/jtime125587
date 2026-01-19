package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static it.unicam.cs.mpgc.jtime125587.view.Main.openDialog;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Controller della vista che gestisce la lista dei progetti.
 * Fornisce metodi per inizializzare la tabella, aggiornare i dati,
 * aprire la finestra per aggiungere un progetto, terminare ed eliminare progetti.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class Projects {
    /**
     * Controller generico per le operazioni CRUD su {@code Task}.
     */
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    /**
     * Controller generico per le operazioni CRUD su {@code Project}.
     */
    private final Controller<Project> projectController = new HibernateController<>(Project.class);
    /**
     * Tabella UI che mostra l'elenco dei progetti.
     */
    @FXML
    private TableView<Project> projectList;
    /**
     * Colonna che mostra il nome del progetto.
     */
    @FXML
    private TableColumn<Project, String> name;
    /**
     * Colonna che mostra la data di inizio del progetto.
     */
    @FXML
    private TableColumn<Project, String> start;
    /**
     * Colonna che mostra la data di fine del progetto.
     */
    @FXML
    private TableColumn<Project, String> end;
    /**
     * Colonna che mostra la durata totale del progetto.
     */
    @FXML
    private TableColumn<Project, String> duration;
    /**
     * Colonna che mostra lo stato corrente del progetto.
     */
    @FXML
    private TableColumn<Project, Status> status;

    /**
     * Inizializza i componenti della view:
     * - Configura i cell value factory per le colonne della tabella;
     * - Viene calcolata la data di inizio/fine e la durata totale
     *   a partire dalle task correlate;
     * - Ricarica la lista dei progetti.
     */
    public void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        start.setCellValueFactory(cellData -> {
            LocalDate date = getTasksOf(cellData.getValue()).map(Task::getDate).min(LocalDate::compareTo).orElse(null);
            if(date != null) return new SimpleObjectProperty<>(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return new SimpleObjectProperty<>("-Not available-");
        });
        end.setCellValueFactory(cellData -> {
            LocalDate date = getTasksOf(cellData.getValue()).map(Task::getDate).max(LocalDate::compareTo).orElse(null);
            if(date != null) return new SimpleObjectProperty<>(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return new SimpleObjectProperty<>("-Not available-");
        });
        duration.setCellValueFactory(cellData -> {
            Duration duration = getTasksOf(cellData.getValue()).map(Task::getDuration).reduce(Duration::plus).orElse(Duration.ZERO);
            if(duration != Duration.ZERO) return new SimpleObjectProperty<>(duration.toHours() + " h " + (duration.toMinutesPart()) + " m");
            return new SimpleObjectProperty<>("-Not available-");
        });
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        refresh();
    }

    /**
     * Ricarica l'elenco dei progetti dalla sorgente dati e aggiorna la TableView.
     */
    public void refresh() {
        projectList.setItems(observableArrayList(projectController.getAll()));
    }

    /**
     * Restituisce uno stream di {@link Task} associati al progetto fornito.
     *
     * @param project il progetto di cui ottenere le task
     * @return stream di task appartenenti al progetto
     */
    private Stream<Task> getTasksOf(Project project) {
        return taskController.getAll().stream().filter(task -> {
            if(task.getProject() != null) return task.getProject().getId().equals(project.getId());
            return false;
        });
    }

    /**
     * Apre la finestra di dialog per aggiungere un nuovo progetto.
     *
     * @throws IOException se il caricamento della risorsa FXML fallisce
     */
    @FXML
    private void openAddProject() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddProject.fxml", "Add Project", null);
        refresh();
    }

    /**
     * Aggiorna il progetto selezionato come COMPLETED se tutte le sue task sono completate.
     */
    @FXML
    private void endProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(project != null && getTasksOf(project).allMatch(task -> task.getStatus() == Status.COMPLETED)) {
            project.setStatus(Status.COMPLETED);
            projectController.update(project);
            refresh();
        }
    }

    /**
     * Elimina il progetto selezionato solamente se non ha task associate.
     */
    @FXML
    private void deleteProject() {
        Project project = projectList.getSelectionModel().getSelectedItem();
        if(project != null && getTasksOf(project).findAny().isEmpty()) {
            projectController.delete(project);
            refresh();
        }
    }
}
