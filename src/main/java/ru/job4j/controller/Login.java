package ru.job4j.controller;

import org.json.JSONObject;
import ru.job4j.model.Customer;
import ru.job4j.store.SqlCustomer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SqlCustomer sqlCustomer = SqlCustomer.getInstance();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (!login.equals("") && !password.equals("")) {
            Customer customer = sqlCustomer.getCustomerByLoginAndPassword(login, password);
            req.getSession().setAttribute("customer", customer);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", "Вы успешно авторезовались!");
            try (PrintWriter printWriter = new PrintWriter(resp.getOutputStream())) {
                printWriter.println(jsonObject.toString());
            }
        }
    }
}
