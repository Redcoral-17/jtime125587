package it.unicam.cs.mpgc.jtime125587;

import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error building SessionFactory: " + ex);
        }
    }

    public static <R> R doInSess(@NonNull Function<Session, R> action) {
        try(Session session = sessionFactory.openSession()) {
            return action.apply(session);
        }
    }

    public static void doInTx(@NonNull Consumer<Session> action) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
        }
    }
}
