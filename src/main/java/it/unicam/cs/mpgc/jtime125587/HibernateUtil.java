package it.unicam.cs.mpgc.jtime125587;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch(Exception ex) {
            throw new IllegalArgumentException("Error while building session factory: " + ex);
        }
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}
