package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProjectController {
    @Getter
    private static final ProjectController instance = new ProjectController();

    public void add(@NonNull Project project) {
        HibernateUtil.runInTx((Consumer<Session>) session -> session.persist(project));
    }

    private LocalDate dateOf(@NonNull Task task) {
        return task.getDate();
    }

    public Duration totalOf(@NonNull Project project) {
        Duration tot = Duration.ZERO;
        for(Task task : project.getTasks()) {
            tot = tot.plus(TaskController.timeOf(task));
        }
        return tot;
    }

    public List<String> getProjectNames() {
        return HibernateUtil.runInTx((Function<Session, List<String>>) session ->
                session.createQuery("select name from Project", String.class)
                        .getResultList());
    }

    public Project getProjectByName(@NonNull String name) {
        return HibernateUtil.runInTx((Function<Session, Project>) session ->
                session.createQuery("from Project where name = :name", Project.class)
                        .setParameter("name", name)
                        .getSingleResult());
    }
}
