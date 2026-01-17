package it.unicam.cs.mpgc.jtime125587.controller;

import lombok.NonNull;

import java.util.List;

import static it.unicam.cs.mpgc.jtime125587.controller.HibernateUtil.doInSess;
import static it.unicam.cs.mpgc.jtime125587.controller.HibernateUtil.doInTx;

/**
 * Controller generico basato su Hibernate per operazioni CRUD semplici.
 * Utilizza le utility statiche {@code doInSess} e {@code doInTx} di
 * {@code HibernateUtil} per eseguire rispettivamente operazioni in sola
 * lettura su una Session gestita e operazioni di scrittura in una Transaction.
 *
 * @param <T> tipo dell'entità gestita dal controller
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class HibernateController<T> implements Controller<T> {

    /**
     * Classe dell'entità gestita. Usata per fornire
     * il tipo alle operazioni di Session/Query.
     */
    private final Class<T> entityClass;

    /**
     * Crea un nuovo controller per il tipo di entità specificato.
     *
     * @param entityClass la classe dell'entità da gestire
     */
    public HibernateController(Class<T> entityClass) { this.entityClass = entityClass; }

    /**
     * Aggiunge una nuova istanza dell'entità nel database.
     *
     * @param entity l'entità da aggiungere
     * @throws NullPointerException se {@code entity == null}
     */
    @Override
    public void add(@NonNull T entity) { doInTx(session -> session.persist(entity)); }

    /**
     * Aggiorna un'entità esistente nel database.
     *
     * @param entity l'entità da aggiornare
     * @throws NullPointerException se {@code entity == null}
     */
    @Override
    public void update(@NonNull T entity) { doInTx(session -> session.merge(entity)); }

    /**
     * Rimuove un'entità dal database.
     *
     * @param entity l'entità da rimuovere
     * @throws NullPointerException se {@code entity == null}
     */
    @Override
    public void delete(@NonNull T entity) { doInTx(session -> session.remove(entity)); }

    /**
     * Recupera tutte le istanze dell'entità dal database.
     *
     * @return lista di tutte le istanze dell'entità di tipo {@code T}
     */
    @Override
    public List<T> getAll() {
        return doInSess(session -> session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList());
    }
}
