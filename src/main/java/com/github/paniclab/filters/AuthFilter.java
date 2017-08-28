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
        System.out.println("Поток управления вошел в AuthFilter");

        Profile profile = createProfile(req);
        LOGGER.info("Для последующей проверки создан профиль с именем " + profile.userName());

        ProfileService service = createProfileService(req);

        String mode = req.getParameter("mode");
        LOGGER.info("Параметр mode запроса: " + mode);
        switch (mode) {
            case "login": {
                LOGGER.info("Пользователь пытается залогиниться...");
                if(service.isExist(profile)) {
                    LOGGER.info("Фильтр: в БД такой профиль уже существует.");

                    if(service.isPasswordValid(profile)) {
                        LOGGER.info("Фильтр: пароль для профиля введен верно.");
                        LOGGER.info("Пользователь успешно залогинился.");
                        req.setAttribute("userName", profile.userName());
                        System.out.println("AuthFilter вызывает doFilter()");
                        chain.doFilter(req, resp);
                    } else {
                        LOGGER.info("Фильтр: пароль для профиля введен неверно.");
                        LOGGER.info("Попытка залогиниться завершилась неудачно.");
                        req.setAttribute("head", "Ошибка.");
                        req.setAttribute("message", "Пароль введен неверно.");
                        System.out.println("AuthFilter пробрасывает на error.jsp");
                        req.getRequestDispatcher("/error.jsp").forward(req, resp);
                    }
                } else {
                    LOGGER.info("Попытка залогиниться завершилась неудачно.");
                    req.setAttribute("head", "Ошибка.");
                    req.setAttribute("message", "Пользователя с таким именем не существует.");
                    System.out.println("AuthFilter пробрасывает на error.jsp");
                    req.getRequestDispatcher("/error.jsp").forward(req, resp);
                }
                break;
            }
            case "register": {}
            default: {
                LOGGER.info("Пользователь пытается зарегистрироваться...");
                if(service.isExist(profile)) {
                    LOGGER.info("Фильтр: Пользователь с таким именем существует");

                    req.setAttribute("head", "Ошибка.");
                    req.setAttribute("message", "Пользователя с таким именем уже существует.");
                    System.out.println("AuthFilter пробрасывает на error.jsp");
                    req.getRequestDispatcher("/error.jsp").forward(req, resp);
                } else {
                    LOGGER.info("Фильтр: пользователя с таким именем не существует. Пытаемся сохранить профиль...");
                    service.saveNew(profile);
                    req.setAttribute("userName", profile.userName());
                    System.out.println("AuthFilter вызывает doFilter()");
                    chain.doFilter(req, resp);
                }
            }
        }
        System.out.println("Поток управления покидает в AuthFilter");
    }

    private Profile createProfile(ServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");

        String name = firstName + " " + lastName;
        LOGGER.info("Имя пользователя: " + name);
        return  Profile.valueOf(name, password);
    }

    private ProfileService createProfileService(ServletRequest req) {
        HttpServletRequest request = HttpServletRequest.class.cast(req);
        Connection connection = (Connection) request.getSession().getAttribute("connection");
        LOGGER.info(connection == null ? "Объект Connection равен null!" : "Объект Connection получен успешно.");
        return ProfileService.newInstance(connection);
    }


    public void init(FilterConfig config) throws ServletException {
        LOGGER.info("Auth-фильтр создан успешно.");
    }
}
