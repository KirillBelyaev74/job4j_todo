package ru.job4j.store;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.model.Item;

public class SessionFactoryInstance {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                sessionFactory = config.configure().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
