package it.unicam.cs.mpgc.jtime125587.controller;

public interface Controller<T> {
    void add(T entity);
    void delete(T entity);
}
