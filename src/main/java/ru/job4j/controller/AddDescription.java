package ru.job4j.controller;

import org.json.JSONObject;
import ru.job4j.model.Customer;
import ru.job4j.model.Item;
import ru.job4j.store.SqlItem;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class AddDescription extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String description = req.getParameter("description");
        String[] categories = req.getParameter("categories").split(",");
        Item item = new Item(description, new Timestamp(System.currentTimeMillis()), false);
        item.setCustomer((Customer) req.getSession().getAttribute("customer"));
        item = SqlItem.getInstance().save(item, categories);
        if (item.getId() != 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("item", item.getDescription());
            jsonObject.put("text", "Ваша задача добавлена!");
            try (PrintWriter printWriter = new PrintWriter(resp.getOutputStream())) {
                printWriter.println(jsonObject.toString());
            }
        }
    }
}
