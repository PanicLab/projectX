package com.github.paniclab.servlets;

import com.github.paniclab.models.GameSession;
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

public class EndGameServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Идет обработка результатов игры...");
        processGameResult(request);
        request.getRequestDispatcher("win.jsp").forward(request, response);
    }

    private void processGameResult(HttpServletRequest request) {
        GameSession game = (GameSession)request.getSession().getAttribute("game");
        User user = (User)request.getSession().getAttribute("user");
        updateUserData(user, game);

        UserService service = UserService.newInstance(request);
        service.persist(user);
        LOGGER.info("Операция по обновлению данных пользователя завершена.");

        List<User> userList = service.getUsers();
        request.setAttribute("userList", userList);
        LOGGER.info("Таблица результатов обновлена.");
    }

    private void updateUserData(User user, GameSession game) {
        int gameResult = game.getStageCount();

        user.setLastResult(gameResult);
        if(user.getBestResult() == 0) user.setBestResult(gameResult);
        if(user.getBestResult() > gameResult) user.setBestResult(gameResult);
        float newAverageResult = (user.getAverageResult() * user.getAttemptsCount() + gameResult) /
                (user.getAttemptsCount() + 1);
        user.setAverageResult(newAverageResult);
        user.setAttemptsCount(user.getAttemptsCount() + 1);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
