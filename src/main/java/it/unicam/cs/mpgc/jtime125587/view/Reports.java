package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static it.unicam.cs.mpgc.jtime125587.view.Main.openDialog;
import static javafx.collections.FXCollections.observableList;

/**
 * Controller della vista dei report.
 * Gestisce la visualizzazione dei report salvati, la loro selezione e
 * la popolazione della tabella delle task in base ai filtri del report.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class Reports {
    /**
     * Controller generico per le operazioni CRUD su {@code Task}.
     */
    private final Controller<Task> taskController = new HibernateController<>(Task.class);
    /**
     * Controller generico per le operazioni CRUD su {@code Report}.
     */
    private final Controller<Report> reportController = new HibernateController<>(Report.class);
    /**
     * ComboBox FXML per la selezione del report da visualizzare.
     */
    @FXML
    private ComboBox<String> reportList;
    /**
     * Etichetta FXML per mostrare il progetto del report selezionato.
     */
    @FXML
    private Label project;
    /**
     * Etichette FXML per mostrare la data di inizio del report selezionato.
     */
    @FXML
    private Label startDate;
    /**
     * Etichette FXML per mostrare la data di fine del report selezionato.
     */
    @FXML
    private Label endDate;
    /**
     * Etichette FXML per mostrare il numero di task attive e completate del report selezionato.
     */
    @FXML
    private Label tasksStatus;
    /**
     * Tabella FXML che mostra le task del report selezionato.
     */
    @FXML
    private TableView<Task> reportTable;
    /**
     * Colonna FXML per il nome della task.
     */
    @FXML
    private TableColumn<Task, String> name;
    /**
     * Colonna FXML per la data della task.
     */
    @FXML
    private TableColumn<Task, String> date;
    /**
     * Colonna FXML per lo stato della task.
     */
    @FXML
    private TableColumn<Task, Status> status;
    /**
     * Colonna FXML per la differenza di durata della task rispetto alla durata precedente.
     */
    @FXML
    private TableColumn<Task, String> difference;

    /**
     * Inizializza i componenti della UI:
     * - Imposta il listener sulla ComboBox dei report per aggiornare la tabella.
     * - Configura i cell value factory delle colonne della tabella.
     * - Ricarica la ComboBox con i report disponibili.
     */
    public void initialize() {
        reportList.valueProperty().addListener((obs, oldReport, newReport) -> {
            resetReportTable();
            if(newReport != null) setReportTable();
        });
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        difference.setCellValueFactory(cellData -> {
            if(cellData.getValue().getOldDuration() != null) {
                return new SimpleObjectProperty<>(cellData.getValue().getDuration().minus(cellData.getValue().getOldDuration()).toMinutes() + " m");
            }
            return new SimpleObjectProperty<>("-Not available-");
        });
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        refresh();
    }

    /**
     * Aggiorna gli elementi della ComboBox con i nomi di tutti i report disponibili.
     */
    public void refresh() {
        reportList.setItems(observableList(reportController.getAll().stream().map(Report::getName).toList()));
    }

    /**
     * Restituisce uno stream di Task che rispettano i filtri contenuti in {@code Report}.
     *
     * @param report report contenente i filtri
     * @return stream di Task che soddisfano i filtri del report
     */
    private Stream<Task> getTasksOf(Report report) {
        if(report == null) return Stream.empty();
        return taskController.getAll().stream().filter(task -> {
            if(report.getProject() != null) {
                if(task.getProject() == null || !task.getProject().getName().equals(report.getProject())) return false;
            }
            if(report.getStartDate() != null && report.getEndDate() != null) {
                return !task.getDate().isBefore(report.getStartDate()) && !task.getDate().isAfter(report.getEndDate());
            }
            return true;
        });
    }

    /**
     * Popola la tabella e le etichette con i dati del report selezionato.
     */
    private void setReportTable() {
        Report selectedReport = reportController.getAll().stream().filter(report ->
                report.getName().equals(this.reportList.getValue())).findFirst().orElse(null);
        if(selectedReport == null) return;
        reportTable.setItems(observableList(getTasksOf(selectedReport).toList()));
        if(selectedReport.getProject() != null) {
            project.setText(selectedReport.getProject());
        }
        if(selectedReport.getStartDate() != null && selectedReport.getEndDate() != null) {
            startDate.setText(selectedReport.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            endDate.setText(selectedReport.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        tasksStatus.setText(getTasksOf(selectedReport).filter(task -> task.getStatus() == Status.ACTIVE).count() +
                " / " + getTasksOf(selectedReport).filter(task -> task.getStatus() == Status.COMPLETED).count());
    }

    /**
     * Ripristina la tabella e le etichette ai valori di default.
     */
    private void resetReportTable() {
        reportTable.setItems(observableList(List.of()));
        String s = "-Not available-";
        project.setText(s);
        startDate.setText(s);
        endDate.setText(s);
        tasksStatus.setText(s);
    }

    /**
     * Apre la finestra di dialog per aggiungere un nuovo report.
     *
     * @throws IOException se il caricamento della FXML fallisce
     */
    @FXML
    private void openAddReport() throws IOException {
        openDialog("/it/unicam/cs/mpgc/jtime125587/AddReport.fxml", "Add Report", null);
        refresh();
    }

    /**
     * Elimina il report selezionato.
     */
    @FXML
    private void deleteReport() {
        Report selectedReport = reportController.getAll().stream().filter(report ->
                report.getName().equals(this.reportList.getValue())).findFirst().orElse(null);
        if(selectedReport != null) {
            reportController.delete(selectedReport);
            resetReportTable();
            refresh();
        }
    }
}
