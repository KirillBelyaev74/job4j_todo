package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.job4j.model.Customer;

public class SqlCustomer extends TransactionSession{

    private static class InstanceSqlItem {
        private final static SqlCustomer sqlCustomer = new SqlCustomer();
    }

    public static SqlCustomer getInstance() {
        return SqlCustomer.InstanceSqlItem.sqlCustomer;
    }

    public Customer save(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
        return customer;
    }

    public Customer getItemById(int id) {
        return transaction(session -> session.get(Customer.class, id));
    }

    public Customer getCustomerByLogin(String login) {
        return transaction(session -> {
            Query query = session.createQuery("from Customer where login =: login");
            query.setParameter("login", login);
            return (Customer) query.getSingleResult();
        });
    }

    public Customer getCustomerByLoginAndPassword(String login, String password) {
        return transaction(session -> {
            Query query = session.createQuery("from Customer where login =: login and password =: password");
            query.setParameter("login", login);
            query.setParameter("password", password);
            return (Customer) query.getSingleResult();
        });
    }
}
