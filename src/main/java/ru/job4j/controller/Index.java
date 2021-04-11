package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.job4j.model.Item;
import ru.job4j.store.SqlItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Item> items = SqlItem.getInstance().getAllTheItem();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("items", items);
        try (PrintWriter printWriter = new PrintWriter(resp.getOutputStream())) {
            printWriter.println(jsonObject.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String done = req.getParameter("done");
        List<Item> items;
        if (done.equalsIgnoreCase("all")) {
            items = SqlItem.getInstance().getAllTheItem();
        } else {
            items = SqlItem.getInstance().getAllTheItemByDone(done);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("items", items);
        try (PrintWriter printWriter = new PrintWriter(resp.getOutputStream())) {
            printWriter.println(jsonObject.toString());
        }
    }
}
