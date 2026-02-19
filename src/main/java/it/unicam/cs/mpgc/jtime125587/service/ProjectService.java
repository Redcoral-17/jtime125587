package it.unicam.cs.mpgc.jtime125587.service;

import it.unicam.cs.mpgc.jtime125587.exception.ResourceNotFoundException;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import it.unicam.cs.mpgc.jtime125587.repository.ProjectRepository;
import it.unicam.cs.mpgc.jtime125587.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service per la gestione della business logic dei Project.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    /**
     * Recupera tutti i progetti.
     *
     * @return lista di tutti i progetti
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Recupera un progetto per ID.
     *
     * @param id l'ID del progetto
     * @return il progetto trovato
     * @throws ResourceNotFoundException se il progetto non esiste
     */
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Progetto non trovato con ID: " + id));
    }

    /**
     * Crea un nuovo progetto.
     *
     * @param name il nome del progetto
     * @return il progetto creato
     */
    public Project createProject(String name) {
        Project project = new Project(name);
        return projectRepository.save(project);
    }

    /**
     * Aggiorna un progetto esistente.
     *
     * @param id l'ID del progetto da aggiornare
     * @param name il nuovo nome
     * @return il progetto aggiornato
     */
    public Project updateProject(Long id, String name) {
        Project project = getProjectById(id);
        project.setName(name);
        return projectRepository.save(project);
    }

    /**
     * Completa un progetto (imposta status a COMPLETED).
     *
     * @param id l'ID del progetto
     * @return il progetto aggiornato
     */
    public Project completeProject(Long id) {
        Project project = getProjectById(id);
        project.setStatus(Status.COMPLETED);
        return projectRepository.save(project);
    }

    /**
     * Elimina un progetto solo se non ha task associate.
     *
     * @param id l'ID del progetto da eliminare
     * @throws RuntimeException se il progetto ha task associate
     */
    public void deleteProject(Long id) {
        Project project = getProjectById(id);
        List<Task> tasks = taskRepository.findByProject(project);

        if (!tasks.isEmpty()) {
            throw new RuntimeException("Impossibile eliminare il progetto: ha task associate");
        }

        projectRepository.delete(project);
    }

    /**
     * Recupera tutti i progetti attivi.
     *
     * @return lista dei progetti attivi
     */
    public List<Project> getActiveProjects() {
        return projectRepository.findByStatus(Status.ACTIVE);
    }
}

