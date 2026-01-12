package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.AbstractTask;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import lombok.NonNull;

import java.util.List;

public class HibernateController<T> implements Controller<T> {

    protected final Class<T> entityClass;

    public HibernateController(Class<T> entityClass) { this.entityClass = entityClass; }

    @Override
    public void add(@NonNull T entity) { HibernateUtil.doInTx(session -> session.persist(entity)); }

    @Override
    public void update(@NonNull T entity) { HibernateUtil.doInTx(session -> session.merge(entity)); }

    @Override
    public void delete(@NonNull T entity) { HibernateUtil.doInTx(session -> session.remove(entity)); }

    @Override
    public List<T> getAll() {
        return HibernateUtil.doInSess(session ->
                session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList());
    }

    @Override
    public T getByName(String name) {
        return HibernateUtil.doInSess(session ->
                session.createQuery("from " + entityClass.getSimpleName() + " where entityClass.getSimpleName() = :name", entityClass)
                        .setParameter("name", name)
                        .getSingleResult());
    }

    public List<AbstractTask> getTasksOf(@NonNull Project project) {
        return HibernateUtil.doInSess(session -> session.createQuery("from AbstractTask where project = :project", AbstractTask.class)
                .setParameter("project", project)
                .getResultList());
    }
}
