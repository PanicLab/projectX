package com.github.paniclab.servlets;

import com.github.paniclab.models.User;
import com.github.paniclab.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

public class GameMenuServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        extractUserListAndSetTo(request);
        getServletContext().getRequestDispatcher("/WEB-INF/templates/menu.jsp").forward(request, response);
    }

    private void extractUserListAndSetTo(HttpServletRequest request) {
        UserService service = getUserService(request);
        List<User> userList = service.getUsers();
        request.setAttribute("userList", userList);
    }

    private UserService getUserService(HttpServletRequest request) {
        Connection connection = (Connection) request.getSession().getAttribute("connection");
        LOGGER.info(connection == null ? "Объект Connection равен null!" : "Объект Connection получен успешно.");
        return UserService.newInstance(connection);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
