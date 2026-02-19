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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task non trovata con ID: " + id));
    }
    public Task createTask(String name, LocalDate date, LocalTime startTime, LocalTime endTime, Long projectId) {
        Project project = null;
        if (projectId != null) {
            project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Progetto non trovato con ID: " + projectId));
        }
        Task task = new Task(name, date, startTime, endTime, project);
        return taskRepository.save(task);
    }
    public Task updateTask(Long id, LocalTime newStartTime, LocalTime newEndTime) {
        Task task = getTaskById(id);
        task.setOldDuration(task.getDuration());
        task.setStartTime(newStartTime);
        task.setEndTime(newEndTime);
        task.setDuration(Duration.between(newStartTime, newEndTime));
        return taskRepository.save(task);
    }
    public Task completeTask(Long id) {
        Task task = getTaskById(id);
        task.setStatus(Status.COMPLETED);
        return taskRepository.save(task);
    }
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }
    public List<Task> getTasksByProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Progetto non trovato con ID: " + projectId));
        return taskRepository.findByProject(project);
    }
    public List<Task> getTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByDateBetween(startDate, endDate);
    }
    public List<Task> getTasksByProjectAndDateRange(Long projectId, LocalDate startDate, LocalDate endDate) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Progetto non trovato con ID: " + projectId));
        return taskRepository.findByProjectAndDateBetween(project, startDate, endDate);
    }
}
