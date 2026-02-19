package it.unicam.cs.mpgc.jtime125587.service;

import it.unicam.cs.mpgc.jtime125587.exception.ResourceNotFoundException;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import it.unicam.cs.mpgc.jtime125587.repository.ReportRepository;
import it.unicam.cs.mpgc.jtime125587.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service per la gestione della business logic dei Report.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final TaskRepository taskRepository;

    /**
     * Recupera tutti i report.
     *
     * @return lista di tutti i report
     */
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    /**
     * Recupera un report per ID.
     *
     * @param id l'ID del report
     * @return il report trovato
     * @throws ResourceNotFoundException se il report non esiste
     */
    public Report getReportById(Long id) {
        return reportRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Report non trovato con ID: " + id));
    }

    /**
     * Crea un nuovo report.
     *
     * @param name nome del report
     * @param startDate data di inizio (opzionale)
     * @param endDate data di fine (opzionale)
     * @param project nome del progetto (opzionale)
     * @return il report creato
     */
    public Report createReport(String name, LocalDate startDate, LocalDate endDate, String project) {
        Report report = new Report(name, startDate, endDate, project);
        return reportRepository.save(report);
    }

    /**
     * Elimina un report.
     *
     * @param id l'ID del report da eliminare
     */
    public void deleteReport(Long id) {
        Report report = getReportById(id);
        reportRepository.delete(report);
    }

    /**
     * Recupera le task filtrate secondo i criteri del report.
     *
     * @param reportId l'ID del report
     * @return lista delle task che soddisfano i filtri del report
     */
    public List<Task> getTasksByReport(Long reportId) {
        Report report = getReportById(reportId);
        List<Task> allTasks = taskRepository.findAll();

        return allTasks.stream()
            .filter(task -> matchesReportCriteria(task, report))
            .collect(Collectors.toList());
    }

    /**
     * Verifica se una task soddisfa i criteri di un report.
     *
     * @param task la task da verificare
     * @param report il report con i criteri
     * @return true se la task soddisfa i criteri
     */
    private boolean matchesReportCriteria(Task task, Report report) {
        // Filtro per progetto
        if (report.getProject() != null) {
            if (task.getProject() == null ||
                !task.getProject().getName().equals(report.getProject())) {
                return false;
            }
        }

        // Filtro per intervallo di date
        if (report.getStartDate() != null && report.getEndDate() != null) {
            LocalDate taskDate = task.getDate();
            return !taskDate.isBefore(report.getStartDate()) &&
                   !taskDate.isAfter(report.getEndDate());
        }

        return true;
    }
}

