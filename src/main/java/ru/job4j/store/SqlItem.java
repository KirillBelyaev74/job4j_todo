package ru.job4j.store;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.job4j.model.Item;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.function.Function;

public class SqlItem {

    private final static Logger LOGGER = Logger.getLogger(SqlItem.class);

    private static final SessionFactory sessionFactory = SessionFactoryInstance.getSessionFactory();

    private static class InstanceSqlItem {
        private final static SqlItem sqlItem = new SqlItem();
    }

    public static SqlItem getInstance() {
        return InstanceSqlItem.sqlItem;
    }

    public Item save(Item item) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        }
        return item;
    }

    public Item getItemById(int id) {
        return transaction(session -> session.get(Item.class, id));
    }

    public List<Item> getAllTheItem() {
        return transaction(session -> session.createQuery("from Item order by id").list());
    }

    public List<Item> getAllTheItemByDone(boolean done) {
        return transaction(session -> session.createQuery("from Item where done =: done order by id")
                .setParameter("done", done).list());
    }

    public void completedItems(int id) {
        transaction(session -> {
            Query query = session.createQuery("update Item set done = true where id =: id");
            query.setParameter("id", id);
            return 0 != query.executeUpdate();
        });
    }

    private <T> T transaction(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        T result = null;
        try {
            session.beginTransaction();
            result = command.apply(session);
            session.getTransaction().commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOGGER.error("Exception " + e + ". Method has argument Function<Session, T> command" + command.toString());
        } finally {
            session.close();
        }
        return result;
    }
}
