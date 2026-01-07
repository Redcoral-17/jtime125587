package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import lombok.Getter;

import java.util.List;

public class ProjectController {
    @Getter
    private static final ProjectController instance = new ProjectController();

    public void add(Project project) {
        HibernateUtil.doInTx(session -> session.persist(project));
    }

    public List<String> getProjectNames() {
        return HibernateUtil.doInSess(session -> session.createQuery("select name from Project", String.class).getResultList());
    }

    public Project getProjectByName(String name) {
        if(name == null) return null;
        return HibernateUtil.doInSess(session -> session.createQuery("from Project where name = :name", Project.class)
                .setParameter("name", name)
                .getSingleResult());
    }
}
