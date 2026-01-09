package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.HibernateUtil;
import lombok.NonNull;

import java.util.List;

public abstract class AbstractController<T> implements Controller<T> {

    protected final Class<T> entityClass;

    public AbstractController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void add(@NonNull T entity) { HibernateUtil.doInTx(session -> session.persist(entity)); }

    @Override
    public void update(@NonNull T entity) { HibernateUtil.doInTx(session -> session.merge(entity)); }

    @Override
    public void delete(@NonNull T entity) { HibernateUtil.doInTx(session -> session.remove(entity)); }

    @Override
    public List<T> getAll() {
        return HibernateUtil.doInSess(session -> session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList());
    }
}
