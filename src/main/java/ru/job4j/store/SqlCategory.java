package ru.job4j.store;

import org.hibernate.Session;
import ru.job4j.model.Category;

import java.util.List;

public class SqlCategory extends TransactionSession {

    private static class InstanceSqlCategory {
        private static final SqlCategory SQL_CATEGORY = new SqlCategory();
    }

    public static SqlCategory getInstance() {
        return InstanceSqlCategory.SQL_CATEGORY;
    }

    public Category save(Category category) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
        return category;
    }

    public Category getCategoryById(int id) {
        return transaction(session -> session.get(Category.class, id));
    }

    public List<Category> getAllCategory() {
        return transaction(session -> session.createQuery("from Category order by id").list());
    }
}
