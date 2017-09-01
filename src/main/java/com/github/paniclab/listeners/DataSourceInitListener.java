package com.github.paniclab.listeners;

import com.github.paniclab.services.CreateSchemaService;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Enumeration;
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

    private DataSource getDataSource() {
        try {
            Context context = (Context) new InitialContext().lookup("java:comp/env");
            return  (DataSource) context.lookup("jdbc/db");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        closeDatabase(context);
        deregisterJdbcDrivers();
        LOGGER.info("Контекст приложения закрыт.");
    }

    private void closeDatabase(ServletContext context) {
        JdbcConnectionPool pool = (JdbcConnectionPool)context.getAttribute("connection_pool");
        if(pool != null) {
            try (Connection connection = pool.getConnection();){
                Statement statement = connection.createStatement();
                statement.executeUpdate("SHUTDOWN ");
                LOGGER.info("База данных успешно закрыта.");
            } catch (SQLException e) {
                LOGGER.warning("Не удалось закрыть базу данных.");
                e.printStackTrace();
            }
        }
    }

    private void deregisterJdbcDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            deregisterDriver(drivers.nextElement());
        }
    }

    private void deregisterDriver(Driver driver) {
        try {
            DriverManager.deregisterDriver(driver);
            LOGGER.info("Драйвер " + driver + " дерегестрирован успешно.");
        } catch (SQLException e) {
            LOGGER.warning("Не удалось дерегестрировать драйвер " + driver);
            e.printStackTrace();
        }
    }



    @Override
    public void sessionCreated(HttpSessionEvent se) {
        JdbcConnectionPool pool =
                (JdbcConnectionPool) se.getSession().getServletContext().getAttribute("connection_pool");
        try {
            Connection connection = pool.getConnection();
            se.getSession().setAttribute("connection", connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Connection connection = (Connection)se.getSession().getAttribute("connection");
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JdbcConnectionPool pool =
                (JdbcConnectionPool) se.getSession().getServletContext().getAttribute("connection_pool");
        pool.dispose();
    }
}
