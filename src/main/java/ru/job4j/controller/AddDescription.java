package ru.job4j.controller;

import org.json.JSONObject;
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
        Item item = new Item(description, new Timestamp(System.currentTimeMillis()), false);
        item = SqlItem.getInstance().save(item);
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