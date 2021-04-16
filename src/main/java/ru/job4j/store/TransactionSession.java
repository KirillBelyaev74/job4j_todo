package ru.job4j.store;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Function;

public class TransactionSession {

    private final static Logger LOGGER = Logger.getLogger(TransactionSession.class);

    protected static final SessionFactory sessionFactory = SessionFactoryInstance.getSessionFactory();

    protected  <T> T transaction(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        Transaction transaction = null;
        T result = null;
        try {
            transaction = session.beginTransaction();
            result = command.apply(session);
            transaction.commit();
        } catch (final Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Exception " + e + ". Method has argument Function<Session, T> command" + command.toString());
        } finally {
            session.close();
        }
        return result;
    }
}
