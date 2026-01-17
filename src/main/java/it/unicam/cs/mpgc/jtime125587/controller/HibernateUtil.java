package it.unicam.cs.mpgc.jtime125587.controller;

import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Utility per la gestione di Hibernate {@code SessionFactory} e per l'esecuzione
 * di operazioni su {@link Session} con gestione automatica delle risorse.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class HibernateUtil {
    /**
     * {@code SessionFactory} condivisa dell'applicazione.
     * Viene costruita all'avvio tramite {@link #buildSessionFactory()}.
     * Lombok fornisce il getter pubblico.
     */
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Costruisce e configura la {@link SessionFactory} usando la configurazione
     * di Hibernate predefinita.
     *
     * @return la {@code SessionFactory} inizializzata
     * @throws IllegalArgumentException se la costruzione fallisce
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error building SessionFactory: " + ex);
        }
    }

    /**
     * Esegue un'azione che ritorna un valore all'interno di una {@link Session}.
     * La sessione viene aperta automaticamente e chiusa al termine dell'azione.
     *
     * @param action funzione che riceve la {@code Session} e ritorna un risultato
     * @param <T> tipo del risultato restituito dall'azione
     * @return il risultato prodotto dall'azione
     * @throws NullPointerException se {@code action == null}
     * @throws RuntimeException se l'azione lancia un'eccezione
     */
    public static <T> T doInSess(@NonNull Function<Session, T> action) {
        try(Session session = sessionFactory.openSession()) {
            return action.apply(session);
        }
    }

    /**
     * Esegue un'azione all'interno di una transazione di una {@link Session}.
     * La sessione viene aperta e la transazione avviata prima di eseguire
     * l'azione; al termine viene eseguita la commit della transazione.
     *
     * @param action consumer che riceve la {@code Session}
     *               e svolge operazioni che richiedono una transazione
     * @throws NullPointerException se {@code action == null}
     * @throws RuntimeException se l'azione lancia un'eccezione
     */
    public static void doInTx(@NonNull Consumer<Session> action) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
        }
    }
}
