package ru.job4j.store;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import ru.job4j.model.Item;
import org.hibernate.SessionFactory;

import java.util.List;

public class SqlItem {

    private final static Logger LOGGER = Logger.getLogger(SqlItem.class);

    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(Item.class).buildSessionFactory();

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

    public Item getItemById(String stringId) {
        int id = 0;
        Item item;
        try {
            id = Integer.parseInt(stringId);
        } catch (Exception e) {
            LOGGER.error("Incorrectly arguments stringId: " + stringId + " in method getItem");
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            item = session.get(Item.class, id);
            session.getTransaction().commit();
        }
        return item;
    }

    public List<Item> getAllTheItem() {
        List<Item> items;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            items = session.createQuery("from Item order by id").list();
            session.getTransaction().commit();
        }
        return items;
    }

    public List<Item> getAllTheItemByDone(String stringDone) {
        boolean done;
        try {
            done = Boolean.parseBoolean(stringDone);
        } catch (Exception e) {
            LOGGER.error("Incorrectly arguments stringDone: " + stringDone + " in method getAllTheItemByDone");
            return null;
        }
        List<Item> items;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            items = session.createQuery("from Item where done =: done order by id")
                    .setParameter("done", done).list();
            session.getTransaction().commit();
        }
        return items;
    }

    public void completedItems(String stringId) {
        int id = 0;
        try {
            id = Integer.parseInt(stringId);
        } catch (Exception e) {
            LOGGER.error("Incorrectly arguments stringId: " + stringId + " in method completedItems");
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("update Item set done = true where id =: id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
    
    public void close() {
        
    }
}
