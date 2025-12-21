package it.unicam.cs.mpgc.jtime125587.controller;

import org.hibernate.Session;

public interface Controller<T> {
    void add(Session session, T entity);
    void delete(Session session, T entity);
}
