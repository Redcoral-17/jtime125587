package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Task;
import lombok.NonNull;

import java.util.List;

import static it.unicam.cs.mpgc.jtime125587.controller.HibernateUtil.doInSess;
import static it.unicam.cs.mpgc.jtime125587.controller.HibernateUtil.doInTx;

public class HibernateController<T> implements Controller<T> {

    private final Class<T> entityClass;

    public HibernateController(Class<T> entityClass) { this.entityClass = entityClass; }

    @Override
    public void add(@NonNull T entity) { doInTx(session -> session.persist(entity)); }

    @Override
    public void update(@NonNull T entity) { doInTx(session -> session.merge(entity)); }

    @Override
    public void delete(@NonNull T entity) { doInTx(session -> session.remove(entity)); }

    @Override
    public List<T> getAll() {
        return doInSess(session -> session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList());
    }

    @Override
    public List<Task> getTasks(T entity) {
        return doInSess(session -> session.createQuery("from Task where id = :entityId", Task.class)
                .setParameter("entityId", entity.hashCode()).getResultList());
    }
}
