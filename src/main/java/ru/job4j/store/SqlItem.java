package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.job4j.model.Item;

import java.util.List;

public class SqlItem extends TransactionSession {

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
}
