package it.unicam.cs.mpgc.jtime125587.repository;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
    List<Task> findByStatus(Status status);
    @Query("SELECT t FROM Task t WHERE t.date BETWEEN :startDate AND :endDate")
    List<Task> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("SELECT t FROM Task t WHERE t.project = :project AND t.date BETWEEN :startDate AND :endDate")
    List<Task> findByProjectAndDateBetween(@Param("project") Project project, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
