package ru.job4j.store;

import org.junit.Test;
import ru.job4j.model.Item;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class SqlItemTest {

    private final SqlItem sqlItem = new SqlItem();

    @Test
    public void whenGetAllTheItems() {
        Item one = new Item("One", new Timestamp(System.currentTimeMillis()), false);
        Item two = new Item("Two", new Timestamp(System.currentTimeMillis()), true);
        Item three = new Item("Three", new Timestamp(System.currentTimeMillis()), false);
        sqlItem.save(one);
        sqlItem.save(two);
        sqlItem.save(three);
        List<Item> items = sqlItem.getAllTheItem();
        assertThat(items.size(), is(3));
    }

    @Test
    public void whenGetItemById() {
        Item four = new Item("Four", new Timestamp(System.currentTimeMillis()), true);
        sqlItem.save(four);
        Item result = sqlItem.getItemById(four.getId());
        assertThat(result.getDescription(), is(four.getDescription()));
    }

    @Test
    public void whenGetAllTheItemsByDone() {
        Item one = new Item("One", new Timestamp(System.currentTimeMillis()), false);
        Item two = new Item("Two", new Timestamp(System.currentTimeMillis()), true);
        Item three = new Item("Three", new Timestamp(System.currentTimeMillis()), false);
        sqlItem.save(one);
        sqlItem.save(two);
        sqlItem.save(three);
        List<Item> items = sqlItem.getAllTheItemByDone(false);
        assertThat(items.size(), is(2));
    }

    @Test
    public void whenAddItemAndCompletedTheItem() {
        Item five = new Item("Five", new Timestamp(System.currentTimeMillis()), false);
        sqlItem.save(five);
        sqlItem.completedItems(five.getId());
        Item item = sqlItem.getItemById(five.getId());
        assertThat(item.isDone(), is(true));
    }
}