package com.github.paniclab.filters;

import com.github.paniclab.models.Profile;
import com.github.paniclab.models.User;
import com.github.paniclab.services.ProfileService;
import com.github.paniclab.services.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

public class GameMenuFilter implements Filter {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public void destroy() {
        LOGGER.info("Auth-фильтр уничтожен.");
    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        Profile profile = createProfile(req);
        LOGGER.info("Для последующей проверки создан профиль с именем " + profile.userName());

        ProfileService service = profileService(req);

        String mode = req.getParameter("mode");
        switch (mode) {
            case "login": {
                LOGGER.info("Пользователь пытается залогиниться...");
                if(service.isExist(profile)) {
                    LOGGER.info("Фильтр: в БД такой профиль уже существует.");

                    if(service.isPasswordValid(profile)) {
                        LOGGER.info("Фильтр: пароль для профиля введен верно.");
                        LOGGER.info("Пользователь успешно залогинился.");
                        User newUser = userService(req).getUserByName(profile.userName());
                        getSession(req).setAttribute("user", newUser);
                        req.setAttribute("userName", profile.userName());
                        chain.doFilter(req, resp);
                    } else {
                        LOGGER.info("Фильтр: пароль для профиля введен неверно.");
                        LOGGER.info("Попытка залогиниться завершилась неудачно.");
                        req.setAttribute("head", "Ошибка.");
                        req.setAttribute("message", "Пароль введен неверно.");
                        req.getRequestDispatcher("/WEB-INF/templates/error.jsp").forward(req, resp);
                    }
                } else {
                    LOGGER.info("Попытка залогиниться завершилась неудачно.");
                    req.setAttribute("head", "Ошибка.");
                    req.setAttribute("message", "Пользователя с таким именем не существует.");
                    req.getRequestDispatcher("/WEB-INF/templates/error.jsp").forward(req, resp);
                }
                break;
            }

            case "register": {}
            default: {
                LOGGER.info("Пользователь пытается зарегистрироваться...");
                if(service.isExist(profile)) {
                    LOGGER.info("Фильтр: Пользователь с таким именем существует");

                    req.setAttribute("head", "Ошибка.");
                    req.setAttribute("message", "Пользователь с таким именем уже существует.");
                    req.getRequestDispatcher("/WEB-INF/templates/error.jsp").forward(req, resp);
                } else {
                    LOGGER.info("Фильтр: пользователя с таким именем не существует. Пытаемся сохранить профиль...");
                    service.persist(profile);
                    req.setAttribute("userName", profile.userName());
                    User newUser = userService(req).getUserByName(profile.userName());
                    getSession(req).setAttribute("user", newUser);
                    chain.doFilter(req, resp);
                }
            }
        }
    }


    private Profile createProfile(ServletRequest req) {
        String name = req.getParameter("firstName");
        String password = req.getParameter("password");
        LOGGER.info("Имя пользователя: " + name);
        return  Profile.valueOf(name, password);
    }


    private ProfileService profileService(ServletRequest req) {
        Connection connection = (Connection)getSession(req).getAttribute("connection");
        LOGGER.info(connection == null ? "Объект Connection равен null!" : "Объект Connection получен успешно.");
        return ProfileService.newInstance(connection);
    }

    private HttpSession getSession(ServletRequest request) {
        return HttpServletRequest.class.cast(request).getSession();
    }


    private UserService userService(ServletRequest req) {
        Connection connection = (Connection)getSession(req).getAttribute("connection");
        LOGGER.info(connection == null ? "Объект Connection равен null!" : "Объект Connection получен успешно.");
        return UserService.newInstance(connection);
    }


    public void init(FilterConfig config) throws ServletException {
        LOGGER.info("Auth-фильтр создан успешно.");
    }
}
