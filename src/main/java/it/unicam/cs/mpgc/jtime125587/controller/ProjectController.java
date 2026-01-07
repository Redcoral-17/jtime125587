package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.util.List;

public class ProjectController {
    @Getter
    private static final ProjectController instance = new ProjectController();

    public void add(@NonNull Project project) {
        HibernateUtil.doInTx(session -> session.persist(project));
    }

    public void update(@NonNull Project project) { HibernateUtil.doInTx(session -> session.merge(project)); }

    public void delete(@NonNull Project project) { HibernateUtil.doInTx(session -> session.remove(project)); }

    public List<Project> getAll() {
        return HibernateUtil.doInSess(session -> session.createQuery("from Project", Project.class).getResultList());
    }

    public boolean statusOf(@NonNull Project project) {
        return TaskController.getInstance().getAllOf(project)
                .stream()
                .allMatch(task -> task.getStatus() == Status.COMPLETED);
    }

    public Duration timeOf(@NonNull Project project) {
        return TaskController.getInstance().getAllOf(project)
                .stream()
                .map(TaskController.getInstance()::timeOf)
                .reduce(Duration.ZERO, Duration::plus);
    }
}
