package ru.job4j.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "done")
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    public Item() {
    }

    public Item(String description, Timestamp created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id && done == item.done
                && Objects.equals(description, item.description)
                && Objects.equals(created, item.created)
                && Objects.equals(customer, item.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done, customer);
    }

    @Override
    public String toString() {
        return "Item { "
                + "id = " + id
                + ", description = '" + description + '\''
                + ", created = " + created
                + ", done = " + done
                + ", customer = " + customer
                + '}';
    }
}
