package it.unicam.cs.mpgc.jtime125587.controller;

import java.util.List;

public interface Controller<T> {
    void add(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> getAll();
    List<String> getNames();
    T getByName(String name);
}
