package ru.job4j.controller;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.job4j.model.Item;
import ru.job4j.store.SqlItem;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Index extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(Index.class);

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
        List<Item> items = null;
        String parameter = req.getParameter("done");
        if (("all").equalsIgnoreCase(parameter)) {
            items = SqlItem.getInstance().getAllTheItem();
        } else {
            try {
                boolean done = Boolean.parseBoolean(req.getParameter("done"));
                items = SqlItem.getInstance().getAllTheItemByDone(done);
            } catch (Exception e) {
                LOGGER.error("Incorrectly parameters done " + parameter);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("items", items);
        try (PrintWriter printWriter = new PrintWriter(resp.getOutputStream())) {
            printWriter.println(jsonObject.toString());
        }
    }
}
