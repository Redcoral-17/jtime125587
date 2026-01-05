package it.unicam.cs.mpgc.jtime125587;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration().configure();
            return cfg.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Error building SessionFactory: " + ex);
        }
    }

    public static <T> T runInTransaction(Function<Session, T> action) {
        Objects.requireNonNull(action, "action must not be null");
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            T result = action.apply(session);
            tx.commit();
            return result;
        } catch (Exception ex) {
            if (tx != null && tx.getStatus().canRollback()) {
                try { tx.rollback(); } catch (Exception ignore) {}
            }
            throw (RuntimeException) ex;
        }
    }

    public static void runInTransaction(Consumer<Session> action) {
        runInTransaction(session -> {
            action.accept(session);
            return null;
        });
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
