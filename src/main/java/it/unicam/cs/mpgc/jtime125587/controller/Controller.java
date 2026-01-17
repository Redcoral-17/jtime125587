package it.unicam.cs.mpgc.jtime125587.controller;

import java.util.List;

/**
 * Interfaccia generica per un controller che gestisce operazioni CRUD su entità di tipo {@code T}.
 *
 * @param <T> il tipo dell'entità gestita dal controller
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public interface Controller<T> {
    /**
     * Aggiunge una nuova entità.
     *
     * @param entity l'entità da aggiungere
     */
    void add(T entity);
    /**
     * Aggiorna un'entità esistente.
     *
     * @param entity l'entità con i nuovi valori
     */
    void update(T entity);
    /**
     * Elimina un'entità.
     *
     * @param entity l'entità da rimuovere
     */
    void delete(T entity);
    /**
     * Restituisce tutte le entità.
     *
     * @return una lista contenente tutte le entità di tipo {@code T}
     */
    List<T> getAll();
}
