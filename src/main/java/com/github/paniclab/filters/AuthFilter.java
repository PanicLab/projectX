package com.github.paniclab.filters;

import com.github.paniclab.models.Profile;
import com.github.paniclab.services.ProfileService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    public void destroy() {
        LOGGER.info("Auth-фильтр уничтожен.");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");
        String message = firstName + lastName + password;

        String name = firstName + " " + lastName;
        LOGGER.info("Имя пользователя: " + name);
        HttpServletRequest request = HttpServletRequest.class.cast(req);
        Connection connection = (Connection) request.getSession().getAttribute("connection");
        LOGGER.info(connection == null ? "Объект Connection равен null!" : "Объект Connection " + "получен успешно.");

        Profile profile = Profile.valueOf(name, password);
        LOGGER.info("Создан профиль с именем " + profile.userName());
        ProfileService service = ProfileService.newInstance(connection);
        if (service.isExist(profile)) {
            LOGGER.info("Такой профиль существует.");
            if (service.isPasswordValid(profile)) {
                LOGGER.info("Пароль введен верно.");
            } else {
                LOGGER.info("Пароль не верный.");
            }
        } else {
            service.saveNew(profile);
            LOGGER.info("Новый профиль сохранен.");
        }

        LOGGER.info(message);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        LOGGER.info("Auth-фильтр создан успешно.");
    }
}
