package com.github.paniclab.listeners;

import com.github.paniclab.services.CreateSchemaService;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.*;
import java.util.logging.Logger;

public class DataSourceInitListener implements ServletContextListener, HttpSessionListener {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public DataSourceInitListener() {
    }


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        buildJdbcConnectionPool(context);
        createSchema(context);
        LOGGER.info("Контекст приложения инициализирован.");
    }

    private void buildJdbcConnectionPool(ServletContext cxt) {
        final String BASE_DIR = cxt.getRealPath("/");
        System.out.println("Базовая: " + BASE_DIR);
        final String DB_RELATIVE_PATH = cxt.getInitParameter("db.relative_path");
        final String URL = "jdbc:h2:" + BASE_DIR + DB_RELATIVE_PATH;
        System.out.println("Вот такая итоговая URL базы данных: " + URL);
        final String USER = cxt.getInitParameter("db.user");
        final String PASS = cxt.getInitParameter("db.password");
        JdbcConnectionPool pool = JdbcConnectionPool.create(URL, USER, PASS);
        cxt.setAttribute("connection_pool", pool);
        LOGGER.info("Объект JdbcConnectionPool создан.");
        LOGGER.info("URL базы данных: " + URL);
        LOGGER.info("Логин пользователя: " + USER);
        LOGGER.info("Пароль пользователя: " + PASS);
    }

    private void createSchema(ServletContext context) {
        CreateSchemaService schemaService = CreateSchemaService.create(context);
        schemaService.createSchema();
        schemaService.close();
    }



    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        closeDatabase(context);
        deregisterDriver("jdbc:h2:org.h2.Driver");
        LOGGER.info("Контекст приложения закрыт.");
    }

    private void closeDatabase(ServletContext context) {
        JdbcConnectionPool pool = (JdbcConnectionPool)context.getAttribute("connection_pool");
        if(pool != null) {
            try (Connection connection = pool.getConnection()){
                Statement statement = connection.createStatement();
                statement.executeUpdate("SHUTDOWN ");
                LOGGER.info("База данных успешно закрыта.");
            } catch (SQLException e) {
                LOGGER.warning("Не удалось закрыть базу данных.");
                e.printStackTrace();
            }
        }
    }

    private void deregisterDriver(String url) {
        try {
            Driver driver = DriverManager.getDriver(url);
            DriverManager.deregisterDriver(driver);
            LOGGER.info("Драйвер " + url + " дерегестрирован успешно.");
        } catch (SQLException e) {
            LOGGER.warning("Не удалось дерегестрировать драйвер " + url);
            e.printStackTrace();
        }
    }



    @Override
    public void sessionCreated(HttpSessionEvent se) {
        bindConnection(se);
        LOGGER.info("Сессия " + se.getSession() + " создана успешно.");
    }

    private void bindConnection(HttpSessionEvent se) {
        JdbcConnectionPool pool =
                (JdbcConnectionPool) se.getSession().getServletContext().getAttribute("connection_pool");
        try {
            Connection connection = pool.getConnection();
            se.getSession().setAttribute("connection", connection);
            LOGGER.info("Объект connection присединен к сессии " + se.getSession());
        } catch (SQLException e) {
            LOGGER.severe("Не удалось присоединить объект connection к сессии " + se.getSession());
            e.printStackTrace();
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        closeBindingConnection(se);
        LOGGER.info("Сессия " + se.getSession() + " закрыта.");
    }

    private void closeBindingConnection(HttpSessionEvent se) {
        Connection connection = (Connection)se.getSession().getAttribute("connection");
        if(connection != null) {
            try {
                connection.close();
                LOGGER.info("Присоединенный к сессии объект Connection закрыт.");
            } catch (SQLException e) {
                LOGGER.warning("Не удалось закрыть объект Connection при закрытии сессии.");
                e.printStackTrace();
            }
        }
    }
}
