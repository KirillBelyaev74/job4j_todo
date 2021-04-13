package ru.job4j.controller;

import org.json.JSONObject;
import ru.job4j.store.SqlItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Completed extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] itemsId = req.getParameter("itemsId").split(",");
        Arrays.stream(itemsId).forEach(i -> SqlItem.getInstance().completedItems(Integer.parseInt(i)));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "Задачи Выполнены!");
        try (PrintWriter printWriter = new PrintWriter(resp.getOutputStream())) {
            printWriter.println(jsonObject.toString());
        }
    }
}
