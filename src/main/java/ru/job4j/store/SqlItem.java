package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.job4j.model.Category;
import ru.job4j.model.Item;

import java.util.List;

public class SqlItem extends TransactionSession {

    private static class InstanceSqlItem {
        private final static SqlItem SQL_ITEM = new SqlItem();
    }

    public static SqlItem getInstance() {
        return InstanceSqlItem.SQL_ITEM;
    }

    public Item save(Item item, String[] categories) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            if (categories.length != 0) {
                for (int index = 0; index != categories.length; index++) {
                    int id = Integer.parseInt(categories[index]);
                    Category category = session.get(Category.class, id);
                    item.addCategory(category);
                }
            }
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
