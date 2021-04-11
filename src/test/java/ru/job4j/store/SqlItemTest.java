package ru.job4j.store;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.Item;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class SqlItemTest {

    private final SqlItem sqlItem = new SqlItem();
    private Item one;
    private Item two;
    private Item three;
    private Item four;
    private Item five;

    @Before
    public void start() {
        one = new Item("One", new Timestamp(System.currentTimeMillis()), false);
        two = new Item("Two", new Timestamp(System.currentTimeMillis()), true);
        three = new Item("Three", new Timestamp(System.currentTimeMillis()), false);
        four = new Item("Four", new Timestamp(System.currentTimeMillis()), true);
        five = new Item("Five", new Timestamp(System.currentTimeMillis()), false);
        sqlItem.save(one);
        sqlItem.save(two);
        sqlItem.save(three);
        sqlItem.save(four);
        sqlItem.save(five);
    }

    @Test
    public void whenGetItemById() {
        Item result = sqlItem.getItemById(String.valueOf(three.getId()));
        assertThat(result.getDescription(), is(three.getDescription()));
    }

    @Test
    public void whenGetAllTheItems() {
        List<Item> items = sqlItem.getAllTheItem();
        assertThat(items.size(), is(5));
    }

    @Test
    public void whenGetAllTheItemsByDone() {
        List<Item> items = sqlItem.getAllTheItemByDone("false");
        assertThat(items.size(), is(3));
    }
}