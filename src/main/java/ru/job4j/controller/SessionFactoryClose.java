package ru.job4j.controller;

import ru.job4j.store.SessionFactoryInstance;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SessionFactoryClose implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactoryInstance.close();
    }
}
