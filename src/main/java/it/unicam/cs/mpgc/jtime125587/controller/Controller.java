package it.unicam.cs.mpgc.jtime125587.controller;

import it.unicam.cs.mpgc.jtime125587.model.Task;

import java.util.List;

public interface Controller<T> {
    void add(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> getAll();
    List<Task> getTasks(T entity);
}
