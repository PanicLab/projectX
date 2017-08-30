package com.github.paniclab.servlets;

import com.github.paniclab.models.GameSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class GameLauncherServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Поток управления входит в GameLauncherServlet");

        GameSession gameSession = (GameSession)request.getSession().getAttribute("game");
        if(gameSession == null) {
            gameSession = GameSession.newInstance();
        }
        if (gameSession.isOver()) {
            gameSession = GameSession.newInstance();
        }
        request.getSession().setAttribute("game", gameSession);
        LOGGER.info("Создан новый объект GameSession. Загадано число: " + gameSession.getNumber());

        request.getRequestDispatcher("/game").forward(request, response);
        System.out.println("Выход из GameLauncherServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
