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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DataSourceInitListener implements ServletContextListener,
        HttpSessionListener {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    // Public constructor is required by servlet spec
    public DataSourceInitListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is
            initialized(when the Web application is deployed).
             You can initialize servlet context related data here.
        */
        ServletContext context = sce.getServletContext();

        final String BASE_DIR = context.getRealPath("/");
        System.out.println("Базовая: " + BASE_DIR);
        final String DB_RELATIVE_PATH = context.getInitParameter("db.relative_path");
        final String URL = "jdbc:h2:" + BASE_DIR + DB_RELATIVE_PATH;
        System.out.println("Вот такая итоговая URL базы данных: " + URL);
        final String USER = context.getInitParameter("db.user");
        final String PASS = context.getInitParameter("db.password");
        JdbcConnectionPool pool = JdbcConnectionPool.create(URL,USER, PASS);
        context.setAttribute("connection_pool", pool);
        LOGGER.info("Объект JdbcConnectionPool создан.");
        LOGGER.info("URL базы данных: " + URL);
        LOGGER.info("Логин пользователя: " + USER);
        LOGGER.info("Пароль пользователя: " + PASS);

        CreateSchemaService.create(context).createSchema();

/*        try (CreateSchemaService schemaService = CreateSchemaService.create(pool.getConnection())) {
            schemaService.createSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
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
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        JdbcConnectionPool pool = (JdbcConnectionPool)sce.getServletContext().getAttribute("connection_pool");

        //TODO clean from production code ------------>
/*        try (CreateSchemaService schemaService = CreateSchemaService.create(pool.getConnection())) {
            schemaService.dropSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        // ------------------------------------------->

        if(pool != null) {
            pool.dispose();
            pool = null;
        }

        LOGGER.info("Context destroyed.");
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
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
        /* Session is destroyed. */
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
